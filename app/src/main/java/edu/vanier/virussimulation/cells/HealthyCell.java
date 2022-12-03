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
 * @author Mark
 */
public class HealthyCell extends Cell {

    public HealthyCell(int hitCounter, double size, double speed, boolean motion) {
        super(size, speed, motion);
        this.hitCounter = 0;

    }

    public HealthyCell() {
        Image healthy1 = new Image("/images/Healthy_Cell_1.png", false);
        this.setFill(new ImagePattern(healthy1));
        this.setIsHealthy(true);
        this.hitCounter = 0;
    }

}
