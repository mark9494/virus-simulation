/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.virussimulation.cells;

import javafx.scene.paint.Color;

/**
 *
 * @author Mark
 */
public class HealthyCell extends Cell {

    // counts how many times the healthy cell has been in contact with a virus cell

    public HealthyCell(int hitCounter, double size, double speed, boolean motion) {
        super(size, speed, motion);
        this.hitCounter = 0;

    }

    public HealthyCell() {
        c = Color.BLUE;
        this.setFill(c);
        this.setIsHealthy(true);
        this.hitCounter = 0;
    }

}
