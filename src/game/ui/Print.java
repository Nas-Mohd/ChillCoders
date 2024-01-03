/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game.ui;

import game.io.SaveDB;
import combat.Combat;
import entity.monsters.Goblin;
import entity.Major;
import entity.Monster;
import entity.Player;
import game.engine.Game;
import game.io.Reader;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Random;

/**
 *
 * @author ChillCoders Group
 */
public class Print {
    static Game window;
    public static String heading;
    public static String mainText;
    public static String textArt;
    static Goblin g;
    static Monster enemy;
    static Major chosenMajor;
    static Player player;
    static String separator = "+----------------------------------------------------+\n";
    static String enter = "<br><br>Hit <b>ENTER</b> to continue";
    
    public static void setWindow(Game e){
        window = e;
    }
    
    public static String getMajors(){
        String majors = """
                        | Artificial Intelligence (<span style='color: lime;'>AI</span>)
                        | Computer System & Network (<span style='color: lime;'>CSN</span>);
                        | Software Engineering (<span style='color: lime;'>SE</span>);
                        | Multimedia Computing (<span style='color: lime;'>MMC</span>);
                        | Information Systems (<span style='color: lime;'>IS</span>)""";
        majors = wrapWithHtml(majors);
        return majors;
    }
    
    public static void setPlayer(entity.Player p) {
        player = p;
    }
    
    public static void showMajors(){
        heading = "Choose Your Major";
        mainText = getMajors();
        textArt = Reader.getAsciiArt("book");
        
        printDisplay();       
    }
    
    public static void showLoadGame() throws SQLException{
        Game.setProgress("Load Game");
        String[][] saveInfo = SaveDB.getSaveInfo();
        mainText = wrapWithHtml(createHtmlTable(saveInfo) + "<br>Hit <b>ENTER</b> to go back to menu");
        heading = "Load Game";
        textArt = Reader.getAsciiArt("load");
        
        printDisplay();
        
    }
    
    public static void showHelp(){
        Game.setProgress("Help Screen");
        heading = "Controls";
        mainText = wrapWithHtml("""
                                Text that are <span style='color: green;'>GREEN</span> are used to highlight words you can input in the CLI below.
                                Text that are <span style='color: red;'>RED</span> are used to highlight either unlockable choices or choices that are on cooldown.
                                
                                In the MAP, use <b>W</b>,<b>A</b>,<b>S</b>,<b>D</b> to move your character <b>up</b>, <b>left</b>, <b>down</b> and <b>right</b> respectively.
                                
                                Hit <b>ENTER</b> to continue
                                
                                Command Line Interface (CLI)
                                <pre'> |
                                 |
                                 |
                                 |
                                \\/
                                </pre>""");
        textArt = Reader.getAsciiArt("help");
        
        printDisplay();
    }
    public static void showHelp2(){
        Game.setProgress("Help Screen2");
        heading = "Stats/Majors";
        mainText = wrapWithHtml("""
                                <span style='color: red;'>ATTACK</span> - determines how much damage your attacks will deal. The HIGHER the attack, the MORE damage dealt.
                                <span style='color: blue;'>DEFENSE</span> - determines how little damage you will receive. The HIGHER the defense, the LESS damage taken.
                                <span style='color: yellow;'>CREDITS</span> - determines how much <span style=' color: red;'>ATTACK</span> and <span style=' color: blue;'>Defense</span> you have, basically this game's "level". The HIGHER the credits, the MORE attack and defense you have.
                                <span style='color: green;'>MAJOR</span> - basically this game's "Classes/Archetypes". Determines the spells available to the player and how much each stat increases per credit. Ex: AI gets +5 attack per credit vs CSN's +2 attack per credit.
                                <span style='color: orange;'>SPELLS</span> - are special actions the player can use during combat. After each use, the player must wait before they can use it once more.
                                Hit <b>ENTER</b> to go back to menu.""");
        textArt = Reader.getAsciiArt("help1");
        
        printDisplay();
    }
    public static void showHelp1(){
        Game.setProgress("Help Screen1");
        heading = "Save/Load System";
        mainText = wrapWithHtml("""
                                This game <b>AUTOSAVES</B> every time the <b>MAP</b> is shown.<br>That is, a <b>SAVE</b> file is created/updated everytime you enter the <b>MAP</b> phase.<br><br><b>SAVES</b> that completes the game and <span style='color: lime;'>WINS</span> are <b>AUTO DELETED</b>.
                                <b>NEW GAME</b> will create a new character and a new <b>SAVE</b>. Only <b>5</b> save slots are available, so if you create a new save/character while there are already <b>5</b> existing <b>SAVE</b> files, the oldest<b> SAVE</b> is <b>DELETED</b>.
                                <b>LOAD GAME</b> will load a previous <b>SAVE</b> and will allow you to continue playing as that instance of the player.
                                
                                Hit <b>ENTER</b> to continue""");
        textArt = Reader.getAsciiArt("load");
        
        printDisplay();
    }
    
    public static void showMajorsInfo(String s) {

            chosenMajor = new Major(s);
            heading = chosenMajor.name;
            mainText = chosenMajor.desc + "\nType '<span style='color: lime;'>y</span>' to choose this class"
                    + "\nHit <b>ENTER</b> to go back";
            mainText = wrapWithHtml(mainText);
            textArt= chosenMajor.ascii;
                
                
                printDisplay();
        
    }
    
    public static void showStory1(){
        Game.setProgress("Story1");
        heading = "Backstory";
        mainText = "It's November 2027 and " + Player.name + " is a " + Player.chosenMajor.name + " final-year student who has just submitted all their requirements. "
                + "You are finally done with the final finals, the last group assignments, your internships and ready to enter the next chapter of your life. "
                +"<br>You look back to all the ups and downs you faced during your uni life. Like the time you overslept and missed one of your exams so they had to beg/bribe the lecturer. "
                + "Or that one time you and your friends went on a roadtrip and had so much fun. You even look back to your first year, where you had to go through the Week Of Welcome."
                + " Waking up 7:00 AM everyday for a week, at the time you just wanted it to end but now you kind of miss it. Oh, and FOP, you are still shocked that you managed to pass that course. <span style='color:blue;'>( •̯́ ^ •̯̀)( •̯́ ₃ •̯̀)</span>\n"
                + enter;
        mainText = wrapWithHtml(mainText);
        textArt = Reader.getAsciiArt("story1");
        
        printDisplay();
    }
    public static void showStory2(){
        Game.setProgress("Story2");
        mainText = "Your reminiscing came to a stop when you heard your Mom yell \"Go get the car!\" to your Dad. It's your"
                + " convocation today, that's why you're wearing your graduation gown. \"" + Player.name + " let's go now. We do not want to be late for your graduation\", you hear your Mom saying from the other room."
                + " As you leave your room, graduation cap in hand, you feel both nervous and excited for today. <b>BUT THEN</b>, something happened as you pass by the door frame of your room.\n"
                + enter;
        mainText = wrapWithHtml(mainText);
        textArt = Reader.getAsciiArt("story2");
        
        printDisplay();
    }
    public static void showStory3(){
        Game.setProgress("Story3");
        mainText = "A portal suddenly appeared in front of you and you get sucked in. For what seemed like an eternity, you fall through a void of nothingness and darkness until you hit solid ground.<br><br>"
                + " You get up and look around, only to find yourself in a different world. A world of fantasy filled with trees as tall as skyscrapers and creatures unlike you have never seen before.\n"
                + enter;
        mainText = wrapWithHtml(mainText);
        textArt = Reader.getAsciiArt("story3");
        
        printDisplay();
    }
    public static void showStory4(){
        Game.setProgress("Story4");
        mainText = wrapWithHtml("As the realization that you are no longer on Earth sets in, a cat suddenly magically teleports in front of you. The cat begins speaking to you,"
                + " \"Hi " + Player.name + ". I am <b>Joe Mama</b>, the caretaker of this world. I apologize, it seemed I made a grave mistake and accidentally transported you to this world."
                + " But do not worry, I will transport you as soon as possible. However, while you were travelling from your world to here, your College Credits were 'falling out'. "
                + "<i><s>(Just pretend it makes sense)</s></i> And they were taken by <b>7 monsters</b> of this world. You have to defeat all<b> 7</b> of them and take your Credits back so you can graduate when I send you back\""
                + enter);
        textArt = Reader.getAsciiArt("story4");
        
        printDisplay();
    }
    public static void showStory(){
        Game.setProgress("Story");
        mainText = wrapWithHtml("After listening to everything <b>Joe Mama</b> said, <b>Joe Mama</b> suddenly levitated into the sky and disappeared from your view. you ready yourself to explore this world and defeat all <b>7 monsters</b> using the knowledge and skills "
                + Player.chosenMajor.name + " has equipped you with."+enter);
        textArt = Reader.getAsciiArt("story");
        
        printDisplay();
    }
    
    public static void printDisplay(){
        window.textArtArea.setText(textArt);
        window.mainTextArea.setText(mainText);
        window.headingLabel.setText(heading);
    }
    
    
    public static void printMonsterEncounter(String monsterName, Monster m){
        enemy = m;
        heading = monsterName.toUpperCase() + " ENCOUNTERED";
            mainText = Combat.enemy.getDesc() + "\nType \"<span style='color: lime;'>FIGHT</span>\" OR \"<span style='color:lime;'>1</span>\" to initiate combat \nHit <b>ENTER</b> to go back";
            mainText = wrapWithHtml(mainText);
            textArt = Combat.enemy.getAscii();
        printDisplay();
    }
    
    public static void monsterStartCombat (Monster enemy) {

        if (player.hp > 0 && enemy.hp > 0) {
            heading = enemy.getName();
            mainText = getPlayerCombatInfo();
            mainText = wrapWithHtml(mainText);
            printDisplay();
        }

    }
    
    public static String getPlayerCombatInfo() {
        String info;
        
        
        info = "Your Stats: \nHP: " + player.hp + "   " 
                + "Attack: " + player.attack + "   "
                + "Defense: " + player.defense + "   "
                + "Credits: " + player.credits + "\n"+ separator
                + "Basic Moves:\n" + "<span style='color: lime;'>1. Attack</span> - Deal basic damage to your opponent" ;

        if (Combat.defendCD > 0) {
            info += "\n<span style='color: red;'>2. Defend - "+ Combat.defendCD + " / 2 (ON COOLDOWN)</span>";
        }
        else
            info +="\n<span style='color: lime;'>2. Defend</span> - Reduce incoming damage once by increasing your defense (2 cd)";
        if (Combat.healCD > 0)
            info +="\n<span style='color: red;'>3. Heal - "+ Combat.healCD + " / 3 (ON COOLDOWN)</span>";
        else
            info += "\n<span style='color: lime;'>3. Heal</span> - Heal 20% of your Max HP (4 cd)";
        info += "\n<span style='color: lime;'>4. Run</span> - Leave combat (has a chance for failure)\n" + separator 
                + "Spells: ";
        for (int i = 0; i < 3; i++)
            if (player.credits >= Player.chosenMajor.availableSpells[i].credits){
                
                if (Player.chosenMajor.availableSpells[i].countdown > 0) {
                    info += "\n<span style='color: red;'>" + Character.toString('a' + i) + ".) " + Player.chosenMajor.availableSpells[i].name + " - " + Player.chosenMajor.availableSpells[i].countdown + " / " + Player.chosenMajor.availableSpells[i].cd + " (ON COOLDOWN)</span>";
                } else {
                    info += "\n<span style='color: lime;'>" + Character.toString('a' + i) + ".) " + Player.chosenMajor.availableSpells[i].name + "</span>" + " - " + Player.chosenMajor.availableSpells[i].desc + " (" +Player.chosenMajor.availableSpells[i].cd + " cd)";
                }
            } else {
                info += "\n<span style='color: red;'>SPELL LOCKED: " + Player.chosenMajor.availableSpells[i].credits + " credits required to unlock</span>";
            }
        
        return info;
    }
    
    public static void displayEffects (int value) {
        if (Game.progress.equalsIgnoreCase("Attacking")) {
            mainText = "You have HIT the " + enemy.name + " for " + value + " dmg";
            
        }
        else if (Game.progress.equalsIgnoreCase("Defending")) {
            mainText = "You ready yourself to take the next hit, increasing your defense by " + value;
            
        }
        else if (Game.progress.equalsIgnoreCase("Healing")) {
            mainText = "You have restored your health by " + value;
            
        }
        else if (Game.progress.equalsIgnoreCase("Running Successful")) {
            mainText = """
                       You raise your fist and start charging towards the monster in front of you. Only to suddenly make a 180 and run as fast as possible.
                       You have ran away SUCCESSFULY. Congratulations, <i>coward...</i>
                       """;
            
        }
        else if (Game.progress.equalsIgnoreCase("Running Failed")) {
            mainText = """
                       You raise your fist and start charging towards the monster in front of you. Only to suddenly make a 180 and run as fast as possible.
                       While running as fast as you can, you trip on your shoelace :[
                       You have FAILED to run away. Better luck next time ;)
                       """;
        }
        else if (Game.progress.equals("Attack Spell")) {
                for (int i = 0; i < 3; i++)
                    if (Player.chosenMajor.availableSpells[i].type.equalsIgnoreCase("attack")){
                        mainText = "You CAST " + Player.chosenMajor.availableSpells[i].name + ". Damaging your opponent greatly. You have DEALT " + value + " dmg!";
                    }
                        
                }            
        else if (Game.progress.equals("Status Spell")) {
                for (int i = 0; i < 3; i++)
                    if (Player.chosenMajor.availableSpells[i].type.equalsIgnoreCase("status") && value < 1)
                        mainText = "You CAST " + Player.chosenMajor.availableSpells[i].name + ". You lower your opponent's power and stats!";
                    else if (Player.chosenMajor.availableSpells[i].type.equalsIgnoreCase("status") && value > 0)
                        mainText = "You CAST " + Player.chosenMajor.availableSpells[i].name + ". You raise your own power! You are now stronger than before.";
                }            
        else if (Game.progress.equals("Defend Spell")) {
                for (int i = 0; i < 3; i++)
                    if (Player.chosenMajor.availableSpells[i].type.equalsIgnoreCase("defend"))
                        mainText = "You CAST " + Player.chosenMajor.availableSpells[i].name + ". Which will completely nullify your enemy's next attack!";
                }
        else if (Game.progress.equals("Heal Spell")){
            for (int i = 0; i< 3; i++)
                if (Player.chosenMajor.availableSpells[i].type.equalsIgnoreCase("heal"))
                    mainText = "You CAST " + Player.chosenMajor.availableSpells[i].name + ".\n" + Player.chosenMajor.availableSpells[i].desc + "\n You were HEALED you for " + value + " HP.";
        }
        
        if (Game.progress.equals("Running Successful")){
        } else {
            Game.setProgress("Displaying Effects");
        }
        mainText = wrapWithHtml(mainText + enter);

            
        printDisplay();
    }
    
    public static void displayMonsterAction(int value) {
        Random rd = new Random();

        int i = rd.nextInt(4);
        if (enemy.hp < 1)
            mainText = "You have KILLED " + enemy.name;
        else if(Combat.isBlocking){
            mainText = enemy.name + " used their ultimate special killing move, but due to the spell you have casted you took ZERO dmg!";   
            Combat.isBlocking = false;
        }
        else
            mainText = "Monster Stats: \nHP: " + enemy.hp + "   Attack: " + enemy.attack + "   Defense: " + enemy.defense 
            + "   Credits Stolen: " + enemy.credits + "\n" + separator + "\n"
            + enemy.name + enemy.attackDialogues[i] + value + " DMG" 
            + enter;
        mainText = wrapWithHtml(mainText);
        
        Combat.reduceCD();
        printDisplay();
    }
    public static void levelUp(Monster enemy){
        heading = "Level Up!";
        mainText = "You have slain " + enemy.name + ". You retrieve what's rightfully yours and gain " + enemy.credits + " CREDITS.\n"
                + "You restore all your hp and start to feel more big brain. You gain +" + (int) (enemy.credits*Player.chosenMajor.hpScaling) + " HP, +" + (int) (enemy.credits*Player.chosenMajor.atkScaling) + " ATK, and +" + (int)(enemy.credits*Player.chosenMajor.defScaling) + " DEF."
                + "You are now one step closer to going back to your world and graduating. (◕‿◕)\n" +enter;
        textArt = Reader.getAsciiArt("credit");
        mainText = wrapWithHtml(mainText);
        
        
        printDisplay();
    }
    
    
    public static void gameLose() {
        heading = "GAME OVER";
        window.headingLabel.setForeground(Color.red);
        window.textArtArea.setForeground(Color.red);
        mainText = wrapWithHtml("Try again next time!");
        textArt = Reader.getAsciiArt("lose");
        Game.setProgress("Game Lose");
        
        printDisplay();
                
    }
    public static void epilogue0(Monster enemy){
        heading = "Max Level Reached!";
        mainText = "You have slain " + enemy.name + ".\nYou retrieve what's rightfully yours and gain " + enemy.credits + " CREDITS.\n"
                + "You have gotten back ALL of 182 Credits you needed to graduate. You gain +" + (int)(enemy.credits*Player.chosenMajor.hpScaling) + " HP, +" + (int)(enemy.credits*Player.chosenMajor.atkScaling) + " ATK, and +" + (int)(enemy.credits*Player.chosenMajor.defScaling) + " DEF."
                + "You are now able to go back to your world and graduate, Yipee!! (◕‿◕)\n" +enter;
        textArt = Reader.getAsciiArt("credit");
        mainText = wrapWithHtml(mainText);
        Game.setProgress("Epilogue0");
        
        
        printDisplay();
        
    }
    public static void epilogue1() {
        heading = "UNKNOWN ENCOUNTERED";
        mainText = wrapWithHtml("""
                   After taking back all of your College Credits, the clouds part and something descends from the sky. A being of immense pressure and power has appeared in front of you.
                   You are shaken to your core by the sheer presence of this creature of absurdity in front of you. You get ready to possibly fight this creature.
                   <b> BUT THEN... </b>""" + enter);
        textArt = Reader.getAsciiArt("epilogue");
        Game.setProgress("Epilogue1");
        
        printDisplay();
    }
    
    public static void epilogue2(){
        heading = "JOE MAMA ENCOUNTERED";
        mainText = wrapWithHtml("""
                   The being that inspired so much fear in you suddenly changed form. \"Hi\" <b>≽^•⩊•^≼</b>, the being turned out to be <b>Joe Mama</b>, The Caretaker of this world. 
                   "Great work... """ + Player.name + """
                                                      . I can now send you back" said <b>Joe Mama</b>. "However if I do send you back, you will lose all your super powers lmao. Do you still want to go back?" 
                                                    Type <span style ='color:lime;'><b>YES</b></span> to go back to your world.
                                                    Type <span style ='color:red;'><b>NO</b></span> to refuse the offer.""");
        textArt = Reader.getAsciiArt("Story4");
        Game.setProgress("Epilogue2");
        
        printDisplay();
    }
    
    public static void epilogue3y(){
        heading = "Offer Accepted";
        window.headingLabel.setForeground(Color.green);
        mainText = wrapWithHtml("""
                                You accept the offer and a portal manifests underneath your feet. You fall through the portal.
                                "Also", said <b>Joe Mama</b> "Congrats on graduating!" <br>(￣ー￣)ｂ<br> You leave this world the same way you arrived, falling through a void of darkness and emptiness for what will seem like an eternity.
                                """ + enter);
        textArt = Reader.getAsciiArt("Epilogue3y");
        Game.setProgress("Epilogue3");
        
        printDisplay();
    }
    public static void epilogue3n(){
        heading = "Offer Declined";
        window.headingLabel.setForeground(Color.red);
        mainText = wrapWithHtml("""
                                 You decline the offer. "Well you don't really have a choice lmao, the people coding this game just want to be done with it lmao" said <b>Joe Mama</b>.
                                 A portal suddenly manifests underneath your feet and you fall through this portal. <i>It's almost like what you chose didn't even matter</i>.""" + enter);
        textArt = Reader.getAsciiArt("Epilogue3n");
        Game.setProgress("Epilogue3");
        
        printDisplay();
    }
    public static void epilogue4(){
        window.headingLabel.setForeground(Color.white);
        heading = "The End";
        mainText = wrapWithHtml("""
                                While going through the portal, you are suddenly awoken by the sound of your mother's voice "Hey, wake up we arrived at Dewan Tunku Canselor".
                                You think to yourself whether you were just dreaming this whole time...
                                
                                <b><span style =' color: lime;'>YOU WIN</span></b>""" + enter);
        Game.setProgress("Game Win");
        textArt = Reader.getAsciiArt("Epilogue4");
        
        printDisplay();
    }
    
    public static void credits(){
        heading = "CREDITS";
        mainText = wrapWithHtml("""
                                <span style='font-size:24pt;'><b>Created by:</b> ChillCoders (▔▀ ‿ ▀ )ლ ▂▂⌇</span>
                                <span style='font-size:12pt;'><i> In partial fulfillment of WIX1002 Fundamentals of Programming</i></span>
                                <pre style='font-size:18pt;'><b>Members:</b> Anas Mohammad
                                         Muhammad Ikhmal Izahan
                                         ChillCoders Grouptine Leow Si Ting
                                         Saad Ahmed Pathan
                                         Raisha Haque
                                
                                <b>Resources:</b> Google Images (for entity sprites)
                                           emojicombos.com (ASCII Art)
                                           Text-Image.com (Image to ASCII
                                           converter)
                                           RyiSnow Youtube Channel (Making a
                                           game in Java playlist)</pre>
                                <b>Special Thanks To:</b> (シ_ _ )シ .°˖✧<b>Chat-GPT</b>✧˖°. """ + enter);
        Game.setProgress("Credits");
        textArt = Reader.getAsciiArt("credits");
        
        printDisplay();
    }
    
    
    
    
    
    
    public static String wrapWithHtml(String text) {
        String htmlText = text.replace("\n", "<br>");
         return "<html><style type='text/css'>body { font-family: 'Sylfaen'; font-size: 20pt; color: white; }</style><body>" + htmlText + "</body></html>";
    }
    public static String ASCIIwrapWithHtml(String ASCIIart) {
         return "<html><style type='text/css'>body { font-family: 'Sylfaen'; font-size: 8pt; color: white; }</style><body><pre>" + ASCIIart + "</pre></body></html>";
    }
    
    private static String createHtmlTable(String[][] data) {
        StringBuilder htmlTable = new StringBuilder("<table>");

        for (String[] row : data) {
            htmlTable.append("<tr>");

            for (String cell : row) {
                htmlTable.append("<td>").append(cell).append("</td>");
            }

            htmlTable.append("</tr>");
        }

        htmlTable.append("</table>");

        return htmlTable.toString();
    }

}
