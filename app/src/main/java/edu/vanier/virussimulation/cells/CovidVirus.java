/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.virussimulation.cells;

import javafx.scene.paint.Color;

/**
 *
 * @author 2161743
 */
public class CovidVirus extends VirusCell {

    public CovidVirus(int hitCounter, double size, double speed, boolean motion, double timeTillRecovery) {
        super(hitCounter, size, speed, motion, timeTillRecovery);
    }

    public CovidVirus() {
        hitCounter = 2;
        this.setFill(Color.PURPLE);
        this.setRadius(15);
    }
    
}
