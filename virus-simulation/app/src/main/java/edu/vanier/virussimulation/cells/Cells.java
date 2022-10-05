/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.virussimulation.cells;


public abstract class Cells {
    
    protected double size;
    protected double speed;
    protected boolean motion;// true if the cell should move and false if it shouldn't
    
    public Cells(double size, double speed, boolean motion){
       this.size = size;
       this.speed = speed;
       this.motion = motion;
        
    }

    public double getSize() {
        return this.size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isMotion() {
        return this.motion;
    }

    public void setMotion(boolean motion) {
        this.motion = motion;
    }
    
    //  other methods will be added when needed
    
}
