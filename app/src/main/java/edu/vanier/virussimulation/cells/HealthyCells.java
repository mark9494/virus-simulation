/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.virussimulation.cells;

/**
 *
 * @author Mark
 */
public class HealthyCells extends Cells {
    
    private int hitCounter; // counts how many times the healthy cell has been in contact with a virus cell
    
    public HealthyCells( int hitCounter,double size, double speed, boolean motion){
       super(size, speed, motion);
       this.hitCounter = hitCounter;
        
        
    }

    public int getHitCounter() {
        return this.hitCounter;
    }

    public void setHitCounter(int hitCounter) {
        this.hitCounter = hitCounter;
    }
    
    
    
}
