/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.virussimulation.cells;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 *
 * @author 2161743
 */
public class FluVirus extends VirusCell {

    public FluVirus(int hitCounter, double size, double speed, boolean motion, double timeTillRecovery) {
        super(hitCounter, size, speed, motion, timeTillRecovery);
    }

//3 hits(less contagious, 700 times(0.7) smaller relative size)
    public FluVirus() {
        Image virus = new Image("/images/Red_Virus.png", false);
        hitCounter = 1;
        this.setFill(new ImagePattern(virus));
        this.setRadius(14);
    }

}
