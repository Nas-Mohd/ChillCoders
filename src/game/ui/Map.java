/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package game.ui;

import game.io.Reader;
import entity.Player;
import game.engine.Game;
import game.engine.Keypress;
import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import tile.Tile;


/**
 *
 * @author ChillCoders Group
 */
public class Map extends JPanel implements Runnable {

    //Screen Settings
    public final int tilesize = 20;//1 tile 20 x 20
    public final int tilecol = 40; //every column have 40 tile
    public final int tilerow = 40; // every row have 40 tile
    public final int gameScreenWidth = tilesize * tilecol;//widthofscreen = 800
    public final int gameScreenHeight = tilesize * tilerow;//height of screen = 800

    //location for obstacle which is 0
    public int[][] tilemap;//end tilemap 40 x 40*/
    

    public Keypress keypress = new Keypress();
    Thread t1;
    Player player = new Player(this, keypress);//pass Map class and keypress class to player class
    Tile tileType = new Tile(this);//pass Map class

    public Map() {
        tilemap = Reader.readMapFile(tilerow, tilecol);
        this.setFocusable(true);//can focus to receive input
        this.setDoubleBuffered(true);
        this.setBounds(0,0,800,800);
        this.setBackground(Color.black);
        this.addKeyListener(keypress);
        Game.setPlayer(player);

    }//end Map constructor

    public void thread() {
        t1 = new Thread(this);//pass Map class to thread constructor
        t1.start();
    }//end thread

    @Override
    public void run() {
        while (t1 != null) {
            position();
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//end thread

    public void position() {
        player.position();
    }

    //draw image
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        tileType.draw(g, tilemap);
        player.draw(g);
     
        
        g.dispose();
    }//end paintComponent

}//end  class