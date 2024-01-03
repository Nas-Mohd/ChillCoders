/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package game.engine;

import game.ui.Map;
import game.ui.Print;
import game.io.SaveDB;
import combat.Combat;
import entity.monsters.Goblin;
import entity.Player;
import entity.monsters.Dragon;
import entity.monsters.Gnoll;
import entity.monsters.Harpy;
import entity.monsters.Ogre;
import entity.monsters.Skeleton;
import entity.monsters.Witch;
import game.io.Reader;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

/**
 *
 * @author ChillCoders Group
 */
public class Game {

    
    public Map map;
    JFrame window;
    Container con;
    JPanel titleGamePanel,  commandLinePanel, mainTextPanel, textArtPanel, headingPanel;
    public JLabel headingLabel;
    public JTextArea commandLine;
    public JTextPane mainTextArea, textArtArea;
    JTextArea titleGameText;
    JScrollPane commandLineScroll;
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 96);

    Font cliFont = new Font ("Sylfaen", Font.PLAIN, 24);
    Font headingFont = new Font("Sylfaen", Font.BOLD, 52);
    Dimension x = new Dimension(815, 800);
    Dimension a = new Dimension(500,500);
    Dimension b = new Dimension(225,450);
    CommandLineInputHandler titleScreenHandler;
    public static String[] playerInfo;
    public static boolean isLoaded;
    static public String progress, major;
    public static Goblin goblin;
    public static Harpy harpy;
    public static Gnoll gnoll;
    public static Dragon dragon;
    public static Witch witch;
    public static Skeleton skeleton;
    public static Ogre ogre;
    public static Player p;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        new Game();
    }
    
    public Game(){
        System.out.println("Starting Game");
        window = new JFrame();
        window.setSize(x);
        window.setTitle("ChillCoder's Game");
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null);
        
        con = window.getContentPane();
        
        titleGamePanel = new JPanel();
        titleGameText = new JTextArea();
        commandLinePanel = new JPanel();
        commandLine = new JTextArea();
        commandLineScroll = new JScrollPane(commandLine);
        titleScreenHandler = new CommandLineInputHandler(this);
        
        mainTextPanel = new JPanel();
        mainTextArea = new JTextPane();
        textArtPanel = new JPanel();
        textArtArea = new JTextPane();
        headingPanel = new JPanel();
        headingLabel = new JLabel();
        
        startGame();
        
    }
    
    public void startGame() {

        progress = "Start Game";

        titleGamePanel.setBounds(0,0,800,600);
        titleGamePanel.setBackground(Color.black);
        titleGamePanel.setVisible(true);
        
        
        titleGameText.setText("In Another World As A UM Computer Science Student: The Game");
        titleGameText.setBounds(25, 25, 775, 575);
        titleGameText.setFocusable(false);
        titleGameText.setEditable(false);
        titleGameText.setForeground(Color.white);
        titleGameText.setBackground(Color.black);
        titleGameText.setFont(titleFont);
        titleGameText.setLineWrap(true);
        titleGameText.setWrapStyleWord(true);
        titleGameText.setVisible(true);
        
        
        
        commandLinePanel.setBounds(0, 650, 815, 150);
        commandLinePanel.setBackground(Color.black);
        commandLinePanel.setVisible(true);
        con.add(commandLinePanel);
        
        commandLine.setText("Press ENTER to start");
        commandLine.setCaretPosition(commandLine.getText().length());
        commandLine.setBackground(Color.black);
        commandLine.setBounds(0, 650, 815, 150);

        commandLine.setForeground(Color.white);
        commandLine.setLineWrap(true);
        commandLine.setFont(cliFont);
        commandLine.setVisible(true);

        commandLine.addKeyListener(titleScreenHandler);
        
        
        
        
        commandLinePanel.add(commandLineScroll);
        titleGamePanel.add(titleGameText);
        con.add(titleGamePanel);
        
        window.setVisible(true);
        Print.setWindow(this);
        Combat.setWindow(this);
        
    }
    
    public void createMenuScreen(){
        progress = "Menu Screen";
        
        mainTextPanel.setBounds(25,125,500,500);
        mainTextPanel.setBackground(Color.black);
        mainTextPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        mainTextPanel.setVisible(true);
        mainTextPanel.setPreferredSize(a);
        con.add(mainTextPanel);
        

        mainTextArea.setBounds(25,125,500,500);
        mainTextArea.setBackground(Color.black);
        mainTextArea.setForeground(Color.white);

        mainTextArea.setPreferredSize(a);
        mainTextArea.setContentType("text/html");
        mainTextArea.setEditable(false);
        
        mainTextArea.setVisible(true);
       
        mainTextPanel.add(mainTextArea);
        

        textArtPanel.setBounds(525, 150, 225,450);
        textArtPanel.setBackground(Color.black);
        textArtPanel.setVisible(true);
        con.add(textArtPanel);
        

        textArtArea.setBounds(525,150,225,450);
        textArtArea.setBackground(Color.black);
        textArtArea.setForeground(Color.white);
        textArtArea.setPreferredSize(b);
        textArtArea.setContentType("text/html");
        //textArtArea.setLineWrap(true);
        textArtArea.setEditable(false);
        textArtArea.setVisible(true);
        textArtArea.setText(Reader.getAsciiArt("portal"));
        textArtPanel.add(textArtArea);
        

        headingPanel.setBounds(25, 25, 725, 75);
        headingPanel.setBackground(Color.black);
        headingPanel.setVisible(true);
        con.add(headingPanel);
        
        isLoaded = false;

        headingLabel.setForeground(Color.white);
        headingLabel.setFont(headingFont);
        headingLabel.setVisible(true);
        headingLabel.setText("MENU");
        headingPanel.add(headingLabel);
        
        String menuText = Reader.getAsciiArt("menu");
        mainTextArea.setText(menuText);
        
        
    }
    
    public void createCharacterScreen(){
        
        setProgress("Create Character");
        

        textArtArea.setText(Reader.getAsciiArt("book"));
        headingLabel.setText("Choose Your Major");

        mainTextArea.setText(Print.getMajors());
        
        
        
        
        
    }
    
    public void titleToMenuScreen() {
            titleGamePanel.setVisible(false);
            titleGameText.setVisible(false);
            createMenuScreen();
        
        
    }
    public void createPlayer() {
        map = new Map();
                
    }
    
    public void createMap() {
        Game.setProgress("Map");
        hideStuff();


        goblin = new Goblin(this,map);
        harpy = new Harpy(this,map);
        ogre = new Ogre(this,map);
        gnoll = new Gnoll(this,map);
        witch = new Witch(this,map);
        dragon = new Dragon(this,map);
        skeleton = new Skeleton(this,map);
        
        window.add(map);
        window.setVisible(true);
       
        map.thread();
        
        SaveDB.saveGame();
       
    }
    
    public void hideMap() {
        map.setVisible(false);
    }
    
    public void showMap() {
        
        map.setVisible(true);
        SaveDB.saveGame();
        
    }
    
    public void hideStuff() {
        headingPanel.setVisible(false);
        textArtPanel.setVisible(false);
        commandLinePanel.setVisible(false);
        mainTextPanel.setVisible(false);
    }
    public void showStuff() {
        headingPanel.setVisible(true);
        textArtPanel.setVisible(true);
        commandLinePanel.setVisible(true);
        mainTextPanel.setVisible(true);
        commandLine.requestFocus();
        commandLine.setCaretPosition(commandLine.getDocument().getLength());
    }
    
    public static void setProgress(String s) {
        progress = s;
    }
    
    public static void setPlayer(Player x) {
        p = x;
        p.setMajor(major);
        Combat.defendCD = 0;
        Combat.healCD = 0;
        for (int i = 0; i < 3; i ++)
            Player.chosenMajor.availableSpells[i].countdown = 0;
        
        CommandLineInputHandler.setPlayer(x);
    }
    
    public void setMajor (String s) {
        major = s;
    }
    
    public void chooseName(){
        headingLabel.setText("Please Enter a Name");
        String nameText = """
                          Type your character's name in the command line and hit ENTER
                          Or just hit <b>ENTER</b> to go back""";
        mainTextArea.setText(Print.wrapWithHtml(nameText));
    }
    
    public static boolean gameWon(){
        boolean gameWon = false;
        if (goblin.isDead && harpy.isDead && skeleton.isDead && dragon.isDead && ogre.isDead && witch.isDead && gnoll.isDead)
            gameWon = true;
        return gameWon;
    }
    
    public void loadGame(int x){
        isLoaded = true;
        playerInfo = SaveDB.loadFile[x];
        
        setMajor(playerInfo[3]);
        
        this.createPlayer();
        createMap();
    }
    
    public static int getMonsterX(String name){
        int x = 0;
        switch (name){
            case "goblin" -> x = goblin.X;
            case "harpy" -> x = harpy.X;
            case "ogre" -> x = ogre.X;
            case "skeleton" -> x = skeleton.X;
            case "gnoll" -> x = gnoll.X;
            case "witch" -> x = witch.X;
            case "dragon" -> x = dragon.X;
            default->{}
        }
        return x;
    }
    public static int getMonsterY(String name){
        int y = 0;
        switch (name){
            case "goblin" -> y = goblin.Y;
            case "harpy" -> y = harpy.Y;
            case "ogre" -> y = ogre.Y;
            case "skeleton" -> y = skeleton.Y;
            case "gnoll" -> y = gnoll.Y;
            case "witch" -> y = witch.Y;
            case "dragon" -> y = dragon.Y;
            default->{}
        }
        return y;
    }
    public static boolean isMonsterDead(String name){
        boolean y = false;
        switch (name){
            case "goblin" -> y = goblin.isDead;
            case "harpy" -> y = harpy.isDead;
            case "ogre" -> y = ogre.isDead;
            case "skeleton" -> y = skeleton.isDead;
            case "gnoll" -> y = gnoll.isDead;
            case "witch" -> y = witch.isDead;
            case "dragon" -> y = dragon.isDead;
            default->{}
        }
        return y;
    }
}



