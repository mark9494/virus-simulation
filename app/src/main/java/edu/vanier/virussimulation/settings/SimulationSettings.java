/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.virussimulation.settings;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.Color;

/**
 *
 * @author 2161743
 */
public class SimulationSettings {

    protected static ArrayList<Integer> direction = new ArrayList<Integer>();
    protected Random rand = new Random();

    public void SimulationSettings() {

    }
    protected int numberOfCells;
    protected int convertHitCounter;
    protected double currentRate = 10;
    protected double cellX = 20;
    protected double cellY = 20;
    protected int healthyDx;
    protected int healthyDy;
    protected int radius;
    protected int virusRadius;
    protected int virusDx;
    protected int virusDy;
    protected Color virusColor;
    protected int amountOfVirus;
    protected double animationDuration = 50;

}
