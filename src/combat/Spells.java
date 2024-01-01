/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package combat;

import game.io.Reader;

/**
 *
 * @author Anas Mohammad 23055727
 */
public class Spells {
    
    public String name, desc, type;
    public double multiplier;
    public int cd, credits, countdown, stat;
    
    public Spells(String s) {
        name = s;
        
        String[] e = Reader.readSpellsFile(s);
        desc = e[0];
        type = e[1];
        multiplier = Double.parseDouble(e[2]);
        credits = Integer.parseInt(e[3]);
        cd = Integer.parseInt(e[4]);
    
}
    
}
