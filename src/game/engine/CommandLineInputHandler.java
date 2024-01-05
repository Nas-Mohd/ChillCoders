/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game.engine;

import game.ui.Print;
import game.io.SaveDB;
import combat.Combat;
import entity.Player;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 *
 * @author ChillCoders Group
 */
class CommandLineInputHandler implements KeyListener {
    
    Game window;
    String recentInput;
    Document document;
    public static Player player;
    // Constructor
    public CommandLineInputHandler(Game a) {
        window = a;
        
    }
    // Sets player
    public static void setPlayer(Player p) {
        player = p;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        
        
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
        
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Runs when Enter key is released
        
        if (e.getKeyCode() == KeyEvent.VK_ENTER && e.getSource() == window.commandLine && window.commandLine.isFocusOwner()) {
            
            if (!Game.progress.equals("Map")) {
                getRecentInput();
            }
            
            
            // sets command line text to default text after every press of Enter key
            window.commandLine.setText("> ");
            window.commandLine.setCaretPosition(window.commandLine.getText().length());
            window.commandLine.setPreferredSize(new Dimension(800, 100));
            
            
        }
        
        
        
    }
    // Gets the user's latest input in the CL
    public void getRecentInput(){
        document = window.commandLine.getDocument();
        try {
            int length = document.getLength();
            recentInput = document.getText(2, length-3);

                checkInput();
        } catch (BadLocationException e) {
        }
        
        
    }
    // Method for checking recent input and valid inputs based on current game progress.
    public void checkInput(){
        switch (Game.progress){
            case "Start Game" -> window.titleToMenuScreen();
            case "Menu Screen" -> {
                if (recentInput.equalsIgnoreCase("new game") || recentInput.equalsIgnoreCase("new") || recentInput.equals("1"))
                    window.createCharacterScreen();  
                else if (recentInput.equalsIgnoreCase("help") || recentInput.equals("3")){
                    Print.showHelp();
                } else if (recentInput.equalsIgnoreCase("load game") || recentInput.equalsIgnoreCase("load") || recentInput.equals("2")){
                    try {
                        Print.showLoadGame();
                    } catch (SQLException ex) {
                        Logger.getLogger(CommandLineInputHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            case "Load Game" ->{
                
                // Convert the string to an int
                try {
                    int intValue = Integer.parseInt(recentInput) - 1;
                    if (intValue >= 0 && intValue < 5){
                        if (SaveDB.loadFile[intValue] != null && SaveDB.loadFile[intValue][0] != null)
                            window.loadGame(intValue);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format: " + recentInput);
                }
                if (recentInput.equals("")){
                    window.createMenuScreen();
                }
            }
            case "Help Screen" -> Print.showHelp1();
            case "Help Screen1" -> Print.showHelp2();
            case "Help Screen2" -> window.createMenuScreen();
            case "Create Character" -> { 
                switch (recentInput.toLowerCase()){
                    case "ai" -> {
                        Print.showMajorsInfo(recentInput.toLowerCase());
                        Game.setProgress("Choosing");
                        window.setMajor("ai");
                    }
                    case "se" -> {
                        Print.showMajorsInfo(recentInput.toLowerCase());
                        Game.setProgress("Choosing");
                        window.setMajor("se");
                    }
                    case "is" -> {
                        Print.showMajorsInfo(recentInput.toLowerCase());
                        Game.setProgress("Choosing");
                        window.setMajor("is");
                    }
                    case "mmc" -> {
                        Print.showMajorsInfo(recentInput.toLowerCase());
                        Game.setProgress("Choosing");
                        window.setMajor("mmc");
                    }
                    case "csn" -> {
                        Print.showMajorsInfo(recentInput.toLowerCase());
                        Game.setProgress("Choosing");
                        window.setMajor("csn");
                    }
                    default -> System.out.println("invalid input");
                }
            }
            case "Choosing" -> {
                if (recentInput.equals("y")){
                    SaveDB.insert = true;
                    Game.setProgress("Choosing Name");
                    Print.chooseName(window);
                }
                else if (recentInput.equals("")){
                    Game.setProgress("Create Character");
                    Print.showMajors();
                }
            }
            case "Choosing Name" -> {
                if (recentInput.equals("")){
                    Print.showMajorsInfo(Game.major);
                
                    Game.setProgress("Choosing");
                } else {
                Player.setName(recentInput);
                window.createPlayer();
                Print.showStory1();
                }
            }
            case "Story1" -> Print.showStory2();
            case "Story2" -> Print.showStory3();
            case "Story3" -> Print.showStory4();
            case "Story4" -> Print.showStory();
            case "Story" -> window.createMap();
            case "Starting Combat" -> {
                if (recentInput.equalsIgnoreCase("Fight") || recentInput.equals("1")) {
                    Combat.startCombat();
                    Game.setProgress("In Combat");
                } else if (recentInput.equals("")) {
                    window.hideStuff();
                    window.showMap();
                    Game.setProgress("Map");
                }
            }
            case "In Combat" -> {
                if (recentInput.equalsIgnoreCase("attack") || recentInput.equals("1")) {
                Game.setProgress("Attacking");
                Combat.playerAction(recentInput);
            }
            else if (recentInput.equalsIgnoreCase("defend") || recentInput.equals("2") && Combat.defendCD == 0) {
                Game.setProgress("Defending");
                Combat.playerAction(recentInput);
            }
            else if (recentInput.equalsIgnoreCase("heal") || recentInput.equals("3") && Combat.healCD == 0) {
                Game.setProgress("Healing");
                Combat.playerAction(recentInput);
            }
            else if (recentInput.equalsIgnoreCase("run") || recentInput.equals("4")) {
                Game.setProgress("Running");
                Combat.playerAction(recentInput);
            }
            for (int i = 0; i < 3; i++)
                if ((recentInput.equalsIgnoreCase(Player.chosenMajor.availableSpells[i].name) || recentInput.equalsIgnoreCase(Character.toString('a' + i))) && Player.chosenMajor.availableSpells[i].countdown==0 && player.credits >= Player.chosenMajor.availableSpells[i].credits){
                     Game.setProgress("Casting Spell");
                     Combat.playerAction(recentInput);
                }
            }
            case "Displaying Effects" ->{
                if (recentInput.equals("")){
                    Game.setProgress("Displaying Monster");
                    Combat.enemyAttacks();
                }
            }
            case "Displaying Monster" -> {
                if (recentInput.equals("")){
                    Game.setProgress("In Combat");
                    Combat.startCombat();
                }
            }
            case "Game Win" -> {
                Print.credits();
            }
            case "Game Lose" -> {
                Print.credits();
            }
            case "Running Successful" -> {
                if (recentInput.equals("")){
                window.hideStuff();
                window.showMap();
            
                Game.setProgress("Map");
                }
            }
            case "Level Up" -> {
                window.hideStuff();
                window.showMap();
            
                Game.setProgress("Map");
            }
            case "Epilogue0" -> Print.epilogue1();
            case "Epilogue1" -> Print.epilogue2();
            case "Epilogue2" -> {
                if (recentInput.equalsIgnoreCase("y") || recentInput.equalsIgnoreCase("yes"))
                    Print.epilogue3y();
                else if (recentInput.equalsIgnoreCase("n")|| recentInput.equalsIgnoreCase("no"))
                    Print.epilogue3n();
            }
            case "Epilogue3" -> Print.epilogue4();
            case "Credits" ->{
                window.hideStuff();
                window.startGame();
            }
            default -> {}
        }
    }
}
 