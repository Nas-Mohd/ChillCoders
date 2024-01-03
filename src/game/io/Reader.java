/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game.io;

import game.ui.Print;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author Anas Mohammad 23055727
 */
public class Reader {
    
    public static String[] readMonsterFile(String monsterName) {
        String[] monsterDesc = new String[9];
        try {
            Scanner reader = new Scanner(new FileInputStream(Paths.get("src/resource/desc/monstersDesc.txt").toFile()));
            while(!reader.nextLine().equals(monsterName)){} // go through file until AFTER monster name
            monsterDesc[0] = reader.nextLine(); // monster description
            monsterDesc[1] = reader.nextLine(); // monster hp AS STRING
            monsterDesc[2] = reader.nextLine(); // monster mp AS STRING
            monsterDesc[3] = reader.nextLine(); // monster attack AS STRING
            monsterDesc[4] = reader.nextLine(); // monster defense AS STRING
            monsterDesc[5] = reader.nextLine(); // monster credits AS STRING
            monsterDesc[6] = reader.nextLine(); // monster attack dialogue
            monsterDesc[7] = reader.nextLine(); // monster attack dialogue
            monsterDesc[8] = reader.nextLine(); // monster attack dialogue
            reader.close();
            } catch (FileNotFoundException e) {
            e.printStackTrace();
            }

        
        return monsterDesc;
    }
    
    public static String[] readMajorsFile (String name) {
        String[] majorDesc = new String[11];
        try {
            Scanner reader = new Scanner(new FileInputStream(Paths.get("src/resource/desc/majorsDesc.txt").toFile()));
            while(!reader.nextLine().equals(name)){} // go through file until AFTER major identifier
            majorDesc[0] = reader.nextLine(); // major name
            majorDesc[1] = reader.nextLine(); // major description
            while (true) {
                String desc = reader.nextLine();
                if (desc.equals("stats"))
                    break;
                majorDesc[1] = majorDesc[1] + "\n" + desc;
            }
            majorDesc[2] = reader.nextLine(); // major hp AS STRING
            majorDesc[3] = reader.nextLine(); // major attack AS STRING
            majorDesc[4] = reader.nextLine(); // major defense AS STRING
            majorDesc[5] = reader.nextLine(); // major hpScale AS STRING
            majorDesc[6] = reader.nextLine(); // major atkScale AS STRING
            majorDesc[7] = reader.nextLine(); // major defScale AS STRING
            majorDesc[8] = reader.nextLine(); // major SpellName 1
            majorDesc[9] = reader.nextLine(); // major SpellName 2
            majorDesc[10] = reader.nextLine(); // major SpellName 3
            reader.close();
            } catch (FileNotFoundException e) {
            e.printStackTrace();
            }
        return majorDesc;
    }
    
    public static String[] readSpellsFile (String name) {
        String[] spellDesc = new String[5];
        try {
            Scanner reader = new Scanner(new FileInputStream(Paths.get("src/resource/desc/spellsDesc.txt").toFile()));
            while (reader.hasNextLine()) {
            String currentLine = reader.nextLine();
            if (currentLine.equals(name)) {
                // Found the desired spell, read the following lines
                spellDesc[0] = reader.nextLine(); // spell desc
                spellDesc[1] = reader.nextLine(); // spell type
                spellDesc[2] = reader.nextLine(); // spell multiplier AS STRING
                spellDesc[3] = reader.nextLine(); // spell credit requirement AS STRING
                spellDesc[4] = reader.nextLine(); // spell cooldown AS STRING
                break; // Exit the loop
            }
        }

            } catch (FileNotFoundException e) {
            System.out.println("File was not found"); 
            }
        return spellDesc;
    }
    
    public static int[][] readMapFile(int x, int y){
        int[][] map = new int[x][y];
        try{
            Scanner sc = new Scanner(new FileInputStream(Paths.get("src/resource/desc/map.txt").toFile()));
            for (int i = 0; i < x; i++)
                for (int j = 0; j < y; j++)
                    map[i][j] = sc.nextInt();
        } catch (FileNotFoundException e){
            System.out.println("File was not found");
        }

                
        return map;
    }

    public static String getAsciiArt(String filename) {
        try {
            Print.textArt = String.join("\n", Files.readAllLines(Paths.get("src/resource/ascii/" + filename + "_ascii.txt")));
        } catch (IOException e) {
            Print.textArt = "";
            System.out.println("Problem with image");
        }
        return Print.ASCIIwrapWithHtml(Print.textArt);
    }
    
}
