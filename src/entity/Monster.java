/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import game.engine.Game;
import game.ui.Map;
import game.io.Reader;

/**
 *
 * @author ChillCoders Group
 */
public class Monster {
    // Parent Class to all specific monster classes
    public boolean isDead;
    public String ascii, name, desc, id;
    public int hp, attack, defense, credits, X, Y;
    public String[] attackDialogues;
    public Game game;
    public Map map;
    // Method to assign the proper stats from the String array returned by readMonsterFile() method.
    public void getStats (String monsterName) {
        String[] e = Reader.readMonsterFile(monsterName);
        desc = e[0];
        hp = Integer.parseInt(e[1]);
        attack = Integer.parseInt(e[2]);
        defense = Integer.parseInt(e[3]);
        credits = Integer.parseInt(e[4]);
        attackDialogues = new String[]{e[5], e[6], e[7],e[8]};
    }
    // Sets current location as empty space, essentially removing the monster from the map
    public void despawn(){
        map.tilemap[Y][X] = 1;
    }
    // Getter method for desc.
    public String getDesc() {
        return desc;
    }
    // Getter method for ascii.
    public String getAscii() {
        return ascii;
    }
    // Getter method for name.
    public String getName() {
        return name;
    }
    
}
