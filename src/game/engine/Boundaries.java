/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game.engine;


/**
 *
 * @author ChillCoders Group
 */
public class Boundaries{
    
    public void limitmovement(int x,int y, int speed,int width, int height) {
        if (x < 0) {
            speed = 0;
            x = 0;
        } else if (x > width) {
            speed = 0;
            x = width;
        } else if (y < 0) {
            speed = 0;
            y = 0;
        } else if (y > height) {
            speed = 0;
            y = height;
        }
    }//end action performed
}