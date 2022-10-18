/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.virussimulation.cells;

/**
 *
 * @author Mark
 */
public class VirusCells extends Cells{
    
     private double timeTillRecovery;
    
    public VirusCells( int hitCounter,double size, double speed, boolean motion, double timeTillRecovery){
       super(size, speed, motion);
       this.timeTillRecovery = timeTillRecovery;
        
        
    }

    public double getTimeTillRecovery() {
        return this.timeTillRecovery;
    }

    public void setTimeTillRecovery(double timeTillRecovery) {
        this.timeTillRecovery = timeTillRecovery;
    }
    
    
    
}
