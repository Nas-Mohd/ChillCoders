/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tile;

import entity.monsters.Dragon;
import entity.monsters.Gnoll;
import entity.monsters.Goblin;
import entity.monsters.Harpy;
import entity.monsters.Ogre;
import entity.monsters.Skeleton;
import entity.monsters.Witch;
import java.awt.Graphics;
import java.io.IOException;
import javax.imageio.ImageIO;
import game.ui.Map;

/**
 *
 * @author ChillCoders Group
 */
public class Tile {

    public boolean collision = false;
    Map game;
    Tiletype[] tile;

    //constructor
    public Tile(Map game) {
        this.game = game;
        tile = new Tiletype[2];

        tileImage();
    }//end constructor

    public void tileImage() {
        try {
            tile[0] = new Tiletype();
            tile[0].img = ImageIO.read(getClass().getResourceAsStream("/resource/images/tile.png"));
        } catch (IOException e) {
            System.out.println("Problem with image");
        }
    }//end tileimage method

    // Renders the image for each case on the map
    public void draw(Graphics g, int[][] tilemap) {

        //draw the obstacle when tilemap is 0
        for (int i = 0; i < tilemap.length; i++) {
            for (int j = 0; j < tilemap[0].length; j++) {
                switch (tilemap[i][j]) {
                    case 0 -> g.drawImage(tile[0].img, game.tilesize * j, game.tilesize * i, game.tilesize, game.tilesize, null);
                    case 2 -> g.drawImage(Goblin.img, game.tilesize * j, game.tilesize * i, game.tilesize, game.tilesize, null);
                    case 3 -> g.drawImage(Harpy.img, game.tilesize * j, game.tilesize * i, game.tilesize, game.tilesize, null);
                    case 4 -> g.drawImage(Skeleton.img, game.tilesize * j, game.tilesize * i, game.tilesize, game.tilesize, null);
                    case 5 -> g.drawImage(Witch.img, game.tilesize * j, game.tilesize * i, game.tilesize, game.tilesize, null);
                    case 6 -> g.drawImage(Ogre.img, game.tilesize * j, game.tilesize * i, game.tilesize, game.tilesize, null);
                    case 7 -> g.drawImage(Gnoll.img, game.tilesize * j, game.tilesize * i, game.tilesize, game.tilesize, null);
                    case 8 -> g.drawImage(Dragon.img, game.tilesize * j, game.tilesize * i, game.tilesize, game.tilesize, null);
                    default -> {}
                }//end switch
            }//end loop j
        }//end loop i
    }//end draw method
}