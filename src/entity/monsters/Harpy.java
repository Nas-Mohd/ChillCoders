package entity.monsters;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import entity.Monster;
import game.engine.Game;
import game.io.Reader;
import game.ui.Map;
import game.io.SaveDB;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
import javax.imageio.ImageIO;


/**
 *
 * @author ChillCoders Group
 */
public class Harpy extends Monster{
    
    public static BufferedImage img;
    public static boolean hasSpawned;
    
    
    public Harpy(Game e, Map f) {
        hasSpawned = false;
        isDead = false;
        game = e;
        map = f;
        id = "harpy";
        harpyImage();      
        ascii = Reader.getAsciiArt(id);
        name = "Smart Harpy";
        getStats("Harpy");
        harpySpawn();
    
    }
    

    
    public void harpyImage() {

        try {
            img = ImageIO.read(getClass().getResourceAsStream("/resource/images/harpy.png"));
        } catch (IOException e) {
            System.out.println("Problem with image");
        }
    }
    public void harpySpawn(){
        if (Game.isLoaded){
            
            try {
                String[] info = SaveDB.readMonsterTable(id);
                isDead = "1".equals(info[2]);
                X = Integer.parseInt(info[0]);
                Y = Integer.parseInt(info[1]);
                if (isDead)
                    map.tilemap[Y][X] = 1;
                else
                    map.tilemap[Y][X] = 3;
                hasSpawned = true;
            } catch (SQLException ex) {
                System.out.println("SQL Exception");
            }
             
        } else {
            while (!hasSpawned) {
              // Generate random coordinates for the skeleton's position
               Random rd = new Random();
               X = rd.nextInt(40);
               Y = rd.nextInt(40);
              // Check if the randomly chosen position is valid (not a solid tile)
              if (map.tilemap[Y][X] == 1) {
                  map.tilemap[Y][X] = 3;
                  hasSpawned = true;
              }
          }  
        }
    
    }

}
