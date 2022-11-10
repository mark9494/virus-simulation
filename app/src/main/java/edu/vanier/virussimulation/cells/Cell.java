    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.virussimulation.cells;

import javafx.scene.shape.Circle;


public abstract class Cell extends Circle {
    
    protected double size;
    protected double speed;
    protected boolean motion;// true if the cell should move and false if it shouldn't
    
    protected double dx;
    protected double dy;

    public Cell(double size, double speed, boolean motion){
       this.size = size;
       this.speed = speed;
       this.motion = motion;
        
    }

        public Cell(){
            
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
    
        public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
    
    
    
    
    
    
    //  other methods will be added when needed
    
}
