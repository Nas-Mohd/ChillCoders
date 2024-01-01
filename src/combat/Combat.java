/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package combat;

import entity.Monster;
import entity.Player;
import game.engine.Game;
import game.ui.Print;
import game.io.SaveDB;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anas Mohammad 23055727
 */
public class Combat {
    static Game window;
    public static Monster enemy;
    static Player player;
    public static boolean isDefending, isBlocking;
    public static int defendCD, healCD;
    
    public static void setWindow (Game e) {
        window = e;
    }
    
    public static void initiateCombat(int monster, Player p) {
        
        String monsterName;
        player = p;
        
        switch (monster) {
            case 2 -> {
                monsterName = "goblin";
                enemy = Game.goblin;
            }
            case 3 -> {
                monsterName = "harpy";
                enemy = Game.harpy;
            }
            case 4 -> {
                monsterName = "bony boi";
                enemy = Game.skeleton;
            }
            case 5 -> {
                monsterName = "witch";
                enemy = Game.witch;
            }
            case 6 -> {
                monsterName = "ogre";
                enemy = Game.ogre;
            }
            case 7 -> {
                monsterName = "gnoll";
                enemy = Game.gnoll;
            }
            case 8 -> {
                monsterName = "dragon";
                enemy = Game.dragon;
            }
            default -> {
                monsterName = "";
                enemy = null;
            }
        }
        Game.setProgress("Starting Combat");
        window.hideMap();
        window.showStuff();
        Print.printMonsterEncounter(monsterName, enemy);
    }
    
    public static void startCombat() {
        System.out.println(enemy.hp);
        if (player.hp > 0 && enemy.hp > 0)
        Print.monsterStartCombat(enemy);
        else
            if(player.hp < 1){
                Print.gameLose();
            }
             
            else if(enemy.hp < 1){
                player.credits += enemy.credits;
                enemy.isDead = true;
                enemy.despawn();
                player.levelUp();
                if (Game.gameWon()){
                    try {
                        SaveDB.deletePlayerAndMonsters(Game.p.id);
                    } catch (SQLException ex) {
                        System.out.println("some error lmao");
                    }
                    Print.epilogue0(enemy);
                    
                }

                else{
                    Game.setProgress("Level Up");
                    Print.levelUp(enemy);
                }
                    
            }
                
    }
    
    public static void playerAttacks() {
        int dmg, dif = player.attack - enemy.defense;
        int x, y;
        x = Math.abs(dif/2);
        y = player.attack/2;
        if (x==0)
            x=1;
        if (y==0)
            y=1;
        Random rd = new Random();
        Game.setProgress("Attacking");
        if (dif <= 0) 
            dmg = rd.nextInt(x) + rd.nextInt(y);  
         else
            dmg = dif + rd.nextInt(y/2, y);
        
        enemy.hp -= dmg;
        
        Print.displayEffects(dmg);
    }
    
    public static void enemyAttacks() {
        int dmg = 0, dif = enemy.attack - player.defense;
        int x, y;
        x = Math.abs(dif);
        y = enemy.attack;
        if (x==0)
            x=1;
        if (y==0)
            y=1;
        if (enemy.hp > 0){
           Random rd = new Random();
            if (dif <= 0) {
                dmg = rd.nextInt(y) - rd.nextInt(x);
                if (dmg < 0)
                    dmg = 0;
            }
            else
                dmg = dif + rd.nextInt(y/2, y);
        
            if (isBlocking) {
                dmg = 0;
            }
            player.hp -= dmg;
        
        if (isDefending) {
            isDefending = false;
            player.defense = Player.chosenMajor.defense + Player.chosenMajor.defScaling*player.credits;
        } 
        }

        
            
        
        Print.displayMonsterAction(dmg);
    }
    
    public static void playerAction (String action) {
        switch (Game.progress) {
            case "Attacking" -> playerAttacks();
            case "Defending" -> playerDefends();
            case "Healing" -> playerHeals();
            case "Running" -> playerRuns();
            case "Casting Spell" -> playerCastsSpell(action);
            default -> {
            }
        }
        
    }
    
    public static void playerCastsSpell(String name){
        for (int i = 0; i < 3; i++){
            if (Player.chosenMajor.availableSpells[i].name.equalsIgnoreCase(name) || Character.toString('a' + i).equalsIgnoreCase(name)){
                castSpell(Player.chosenMajor.availableSpells[i]);
            }
        }
            
    }
    
    public static void playerDefends() {
        int value = (int) (player.defense * 0.2);
        player.defense += value;
        isDefending = true;
        defendCD = 2 + 1;
            
        Print.displayEffects(value);
    }
    
    public static void playerHeals() {
        int value, surplus;
        int maxHP = Player.chosenMajor.hp + (Player.chosenMajor.hpScaling*player.credits);
        value = (int) (0.2 * maxHP);
        
        player.hp += value;
        if (player.hp > maxHP) {
            surplus = player.hp - maxHP;
            player.hp -= surplus;
            value -= surplus;
        }
        healCD = 4 + 1;
        Print.displayEffects(value);
    }
    
    public static void playerRuns() {
        Random rd = new Random();
        if (rd.nextBoolean())
            Game.setProgress("Running Failed");
        else
            Game.setProgress("Running Successful");
        
        Print.displayEffects(0);
    }
    
    public static void reduceCD() {
        if (defendCD > 0)
            defendCD--;
        if (healCD > 0)
            healCD--;
        for (int i = 0; i < 3; i++)
            if (Player.chosenMajor.availableSpells[i].countdown > 0)
                Player.chosenMajor.availableSpells[i].countdown--;
                
    }
    
    public static void castSpell(Spells spell) {
        System.out.println("SPELL TYPE: " + spell.type);
        switch (spell.type) {
            case "status" -> {
                Game.setProgress("Status Spell");
                if (spell.multiplier < 1){
                enemy.attack = (int) (enemy.attack * spell.multiplier);
                enemy.defense = (int) (enemy.defense * spell.multiplier);                    
                }
                else if (spell.multiplier > 1){
                    player.attack = (int) (player.attack * spell.multiplier);
                    player.defense = (int) (player.defense * spell.multiplier);
                }
                spell.countdown = spell.cd + 1;
                Print.displayEffects((int)(spell.multiplier));
            }
            case "attack" -> {
                System.out.println("heard attack");
                Game.setProgress("Attack Spell");
                spell.countdown = spell.cd;
                int dmg = (int) (player.attack * spell.multiplier);
                enemy.hp -= dmg;
                spell.countdown = spell.cd + 1;
                Print.displayEffects(dmg);
            }
            case "defend" -> {
                Game.setProgress("Defend Spell");
                isBlocking = true;
                spell.countdown = spell.cd + 1;
                Print.displayEffects(0);
            }
            case "heal" -> {
                Game.setProgress("Heal Spell");
                spell.countdown = spell.cd + 1;
                int maxHP = Player.chosenMajor.hp + (Player.chosenMajor.hpScaling*player.credits);
                int heal = (int) (maxHP * spell.multiplier);
                player.hp += heal;
                if (player.hp > maxHP){
                    int surplus = player.hp - maxHP;
                    heal -= surplus;
                    player.hp = maxHP;
                }
                Print.displayEffects(heal);
            }
            
            default -> {
            }
        }
    }
    
    
}
