/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.virussimulation.settings;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author 2161743
 */
public class SimulationSettings {

    public static ArrayList<Integer> direction = new ArrayList<Integer>();
    Random rand = new Random();

    public void SimulationSettings() {

        direction.add(-1);
        direction.add(1);
        this.healthyDx = direction.get(rand.nextInt(2));
        this.healthyDy = direction.get(rand.nextInt(2));
    }
    protected int numberOfCells = 25;
    protected int convertHitCounter = 1;
    protected double currentRate = 10;
    protected double cellX = 20;
    protected double cellY = 20;
    protected int healthyDx = 1;
    protected int healthyDy = 1;
    protected int radius = 15;
    protected double animationDuration = 50;

}
