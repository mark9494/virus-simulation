/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.virussimulation.cells;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 *
 * @author 2161743
 */
public class CovidVirus extends VirusCell {

    public CovidVirus(int hitCounter, double size, double speed, boolean motion, double timeTillRecovery) {
        super(hitCounter, size, speed, motion, timeTillRecovery);
    }

    public CovidVirus() {
        Image virus = new Image("/images/coronavirus_2d.png", false);
        hitCounter = 1;
        this.setFill(new ImagePattern(virus));
        this.setRadius(20);
    }
    
}
