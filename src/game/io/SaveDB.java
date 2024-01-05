package game.io;

/**
 *
 * @author ChillCoders Group
 */


import entity.Player;
import game.engine.Game;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.*;

public class SaveDB {
    public static boolean insert;
    private static final String URL = "jdbc:sqlite:src/resource/database/save.db";
    public static String[][] loadFile = new String[5][8];

    static {
        // Initialize the logger at the beginning
        Logger.getLogger("").setLevel(Level.OFF);
    }

    // Method for saving game
    public static void saveGame() {
        try {
            Class.forName("org.sqlite.JDBC");

            try ( Connection connection = DriverManager.getConnection(URL)) {

                // Check the current number of saves
                int currentSaveCount = getCurrentSaveCount(connection);

                // Check if the maximum save limit has been reached
                if (insert)
                    while (currentSaveCount >= 5) {
                        // Delete the oldest save file (
                        deleteOldestSave(connection);
                        currentSaveCount = getCurrentSaveCount(connection);
                        insert = false;
                    }

                // Proceed with the save operation
                
                savePlayer(connection);
                saveMonsters(connection);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    // Method for auto save. Does not have delete save file capability.
    public static void autoSave()  {
        try {
            Class.forName("org.sqlite.JDBC");

            try ( Connection connection = DriverManager.getConnection(URL)) {
                
                savePlayer(connection);
                saveMonsters(connection);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    // Returns number of save counts/rows in save.db player table
    private static int getCurrentSaveCount(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM player");

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        }
    }
    // Method for deleting oldest save
    private static void deleteOldestSave(Connection connection) throws SQLException {
        // Implement logic to identify and delete the oldest save 

        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE FROM goblin WHERE id = (SELECT MIN(id) FROM player)");
            statement.executeUpdate("DELETE FROM harpy WHERE id = (SELECT MIN(id) FROM player)");
            statement.executeUpdate("DELETE FROM dragon WHERE id = (SELECT MIN(id) FROM player)");
            statement.executeUpdate("DELETE FROM gnoll WHERE id = (SELECT MIN(id) FROM player)");
            statement.executeUpdate("DELETE FROM skeleton WHERE id = (SELECT MIN(id) FROM player)");
            statement.executeUpdate("DELETE FROM witch WHERE id = (SELECT MIN(id) FROM player)");
            statement.executeUpdate("DELETE FROM ogre WHERE id = (SELECT MIN(id) FROM player)");
            statement.executeUpdate("DELETE FROM player WHERE id = (SELECT MIN(id) FROM player)");
        }
    }
    // Saves player instance
    private static void savePlayer(Connection connection) throws SQLException {
        if (Game.p.id == -1) {
            // Player ID not set, insert a new row
            try (PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO player (name, x, y, major, credits) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                insertStatement.setString(1, Player.name);
                insertStatement.setInt(2, Game.p.x);
                insertStatement.setInt(3, Game.p.y);
                insertStatement.setString(4, Player.chosenMajor.id);
                insertStatement.setInt(5, Game.p.credits);
                String selectSQL = "SELECT last_insert_rowid() AS id";
                  int rowsAffected = insertStatement.executeUpdate();

                if (rowsAffected > 0) {
                 // Execute a separate select statement to get the auto-incremented ID
                    try (Statement selectStatement = connection.createStatement();
                         ResultSet resultSet = selectStatement.executeQuery(selectSQL)) {
                    if (resultSet.next()) {
                        int generatedId = resultSet.getInt("id");
                        Game.p.setId(generatedId);
            }
            }
                }}} else {
            // Player ID is set, update the existing row
            try (PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE player SET name = ?, x = ?, y = ?, major = ?, credits = ? WHERE id = ?"
            )) {
                updateStatement.setString(1, Player.name);
                updateStatement.setInt(2, Game.p.x);
                updateStatement.setInt(3, Game.p.y);
                updateStatement.setString(4, Player.chosenMajor.id);
                updateStatement.setInt(5, Game.p.credits);
                updateStatement.setInt(6, Game.p.id);

                updateStatement.executeUpdate();
            }
        }
            }
        
    
    // Intermediary method to save all monsters
    private static void saveMonsters(Connection connection) throws SQLException {
        saveMonster(connection, "harpy");
        saveMonster(connection, "goblin");
        saveMonster(connection, "skeleton");
        saveMonster(connection, "gnoll");
        saveMonster(connection, "ogre");
        saveMonster(connection, "dragon");
        saveMonster(connection, "witch");
    }
    // Saves specific monster instance
    private static void saveMonster(Connection connection, String tableName) throws SQLException {
       // Check if the row with the specified ID exists
       boolean rowExists = rowExists(connection, tableName, Game.p.id);

       if (rowExists) {
           // Update the existing row
           String updateStatement = String.format(
                   "UPDATE %s SET x = ?, y = ?, isDead = ? WHERE id = ?",
                   tableName
           );

           try (PreparedStatement statement = connection.prepareStatement(updateStatement)) {
               statement.setInt(1, Game.getMonsterX(tableName));
               statement.setInt(2, Game.getMonsterY(tableName));
               statement.setInt(3, Game.isMonsterDead(tableName) ? 1 : 0);
               statement.setInt(4, Game.p.id);

               statement.executeUpdate();
           }
       } else {
           // Insert a new row
           String insertStatement = String.format(
                   "INSERT INTO %s (id, x, y, isDead) VALUES (?, ?, ?, ?)",
                   tableName
           );

           try (PreparedStatement statement = connection.prepareStatement(insertStatement)) {
               statement.setInt(1, Game.p.id);
               statement.setInt(2, Game.getMonsterX(tableName));
               statement.setInt(3, Game.getMonsterY(tableName));
               statement.setInt(4, Game.isMonsterDead(tableName) ? 1 : 0);

               statement.executeUpdate();
           }
       }
   }
    // Checks whether row exists
    private static boolean rowExists(Connection connection, String tableName, int id) throws SQLException {
        String query = String.format("SELECT 1 FROM %s WHERE id = ?", tableName);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
    // Method to delete save. Invoked when game is won or when new save is inserted and player count is currently 5.
    public static void deletePlayerAndMonsters( int playerId) throws SQLException {
         Connection connection = DriverManager.getConnection(URL);
        // Delete rows from each monster table
        deleteRowsFromMonsterTable(connection, "harpy", playerId);
        deleteRowsFromMonsterTable(connection, "goblin", playerId);
        deleteRowsFromMonsterTable(connection, "skeleton", playerId);
        deleteRowsFromMonsterTable(connection, "gnoll", playerId);
        deleteRowsFromMonsterTable(connection, "ogre", playerId);
        deleteRowsFromMonsterTable(connection, "dragon", playerId);
        deleteRowsFromMonsterTable(connection, "witch", playerId);

        // Delete row from the player table
        deleteRowFromPlayerTable(connection, playerId);
    }
    // Deletes monster save
    private static void deleteRowsFromMonsterTable(Connection connection, String tableName, int playerId) throws SQLException {
        String deleteStatement = String.format("DELETE FROM %s WHERE id = ?", tableName);

        try (PreparedStatement statement = connection.prepareStatement(deleteStatement)) {
            statement.setInt(1, playerId);
            statement.executeUpdate();
        }
    }
    // Deletes player save
    private static void deleteRowFromPlayerTable(Connection connection, int playerId) throws SQLException {
        String deleteStatement = "DELETE FROM player WHERE id = ?";
        System.out.println("deleted");
        try (PreparedStatement statement = connection.prepareStatement(deleteStatement)) {
            statement.setInt(1, playerId);
            statement.executeUpdate();
        }
    }
    // Returns a two dimentional String array representing every save. Used for displaying save files in Load Game Screen
    public static String[][] getSaveInfo() throws SQLException {
        List<String[]> saveInfoList = new ArrayList<>();
        String[] header = {"#", "Name", "Major", "Credits", "Monsters Killed"};
        saveInfoList.add(header);

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SaveDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (Connection connection = DriverManager.getConnection(URL)) {
            String selectSaveInfoStatement = "SELECT id, name, major, credits, x, y FROM player";

            try (PreparedStatement statement = connection.prepareStatement(selectSaveInfoStatement)) {
                ResultSet resultSet = statement.executeQuery();

                int count = 1;
                while (resultSet.next()) {
                    int playerId = resultSet.getInt("id");
                    String playerName = resultSet.getString("name");
                    String chosenMajor = resultSet.getString("major");
                    int credits = resultSet.getInt("credits");
                    int x = resultSet.getInt("x");
                    int y = resultSet.getInt("y");
                    loadFile[count-1][6] = Integer.toString(x);
                    loadFile[count-1][7] = Integer.toString(y);
                    loadFile[count-1][0] = Integer.toString(count);
                    loadFile[count-1][1] = Integer.toString(playerId);
                    loadFile[count-1][2] = playerName;
                    loadFile[count-1][3] = chosenMajor;
                    loadFile[count-1][4] = Integer.toString(credits);
 
                    int monstersKilled = getMonstersKilled(connection, playerId);
                    loadFile[count-1][5] = Integer.toString(monstersKilled);              
                    String[] row = {
                        "<span style='color:lime;'>"+Integer.toString(count) + "</span>",
                        playerName,
                        chosenMajor.toUpperCase(),
                        Integer.toString(credits),
                        Integer.toString(monstersKilled) + " / 7"
                    };
                    
                    saveInfoList.add(row);
                    count++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return saveInfoList.toArray(new String[0][0]);
    }

   
    // Checks how much monsters are isDead for a specific save
    private static int getMonstersKilled(Connection connection, int playerId) throws SQLException {

        int monstersKilled = 0;
        for (String tableName : getMonsterTableNames()) {
            String selectMonsterKilledStatement = String.format("SELECT isDead FROM %s WHERE id = ?", tableName);

            try (PreparedStatement statement = connection.prepareStatement(selectMonsterKilledStatement)) {
                statement.setInt(1, playerId);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next() && resultSet.getInt("isDead") == 1) {
                    monstersKilled++;
                }
            }
        }

        return monstersKilled;
    }
    // Just returns  the names of all monsters.
    private static List<String> getMonsterTableNames() {
        // Add the names of all monster tables
        return Arrays.asList("harpy", "goblin", "skeleton", "gnoll", "ogre", "dragon", "witch");
    }
    // Reads monster table from save.db file. Used for loading game
    public static String[] readMonsterTable(String monsterTableName) throws SQLException {
        String[] monsterInfo = new String[3]; // X, Y, isDead
        try (Connection connection = DriverManager.getConnection(URL)) {
             String selectMonsterInfoStatement = String.format("SELECT x, y, isDead FROM %s WHERE id = ?", monsterTableName);

        try (PreparedStatement statement = connection.prepareStatement(selectMonsterInfoStatement)) {
            statement.setInt(1, Game.p.id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                monsterInfo[0] = String.valueOf(resultSet.getInt("x"));
                monsterInfo[1] = String.valueOf(resultSet.getInt("y"));
                monsterInfo[2] = String.valueOf(resultSet.getInt("isDead"));
            }
        }
        }
       

        return monsterInfo;
    }


}

