/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.virussimulation.controllers;

import edu.vanier.virussimulation.cells.Cell;
import edu.vanier.virussimulation.cells.HealthyCell;
import edu.vanier.virussimulation.cells.VirusCell;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author Mark
 */
public class SimulationWindowController {

    public double randomPositionX1;
    @FXML
    private HBox bottomBar;
    @FXML
    private Button btnStart;
    @FXML
    private Button btnAddCell;
    @FXML
    private Button btnAddVirus;
    @FXML
    private Button btnPause;
    @FXML
    private Button btnReset;
    @FXML
    private Pane pane;
    private int numberOfCells = 25;
    private int convertHitCounter = 1;
    private double currentRate = 1;
    private double cellX = 20;
    private double cellY = 20;
    private int radius = 15;
    private double animationDuration = 50;
    final private Random randomThingy = new Random();

    Timeline timeline;
    protected ArrayList<Cell> cellsArrayList = new ArrayList<Cell>();

    public SimulationWindowController() {

    }

    @FXML
    public void initialize() {
        createAnimation();
        generateCells();

        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            recenterCells();
        });
        pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            //System.out.println("RESIZING THE PANE HEIGHT");
            recenterCells();
        });
    }

    public void generateCells() {
        for (int i = 0; i < numberOfCells; i++) {
            cellX = randomThingy.nextInt(900);
            cellY = randomThingy.nextInt(1000);
            Cell newCell = new HealthyCell();
            newCell.setRadius(radius);
            newCell.setCenterX(cellX);
            newCell.setCenterY(cellY);
            newCell.setDx(1);
            newCell.setDy(1);
            cellsArrayList.add(newCell);
            pane.getChildren().addAll(newCell);
            borderSpawnCorrection(newCell);
        }
    }

    private void disableControlButtons(boolean play, boolean pause, boolean stop) {
        btnStart.setDisable(play);
        btnPause.setDisable(pause);
        btnReset.setDisable(stop);
    }

    public void createAnimation() {
        timeline = new Timeline(
                new KeyFrame(Duration.millis(animationDuration), e -> handleUpdateAnimation()));
        timeline.setRate(currentRate);
        timeline.setCycleCount(Timeline.INDEFINITE);

    }

    public void handleStart() {
        disableControlButtons(true, false, false);
        timeline.play();
    }

    public void handleStop() {
        disableControlButtons(false, true, true);
        timeline.pause();
    }

    public void handleReset() {
        disableControlButtons(false, true, true);
        for (Cell c : cellsArrayList) {
            pane.getChildren().remove(c);
        }
        cellsArrayList.removeAll(cellsArrayList);
        generateCells();
        timeline.stop();

    }

    public void handleAddVirus() {
        addVirusCell();
    }

    public void handleAddCell() {
        addHealhyCell();
    }

    private void handleUpdateAnimation() {
        moveBall();
        collide();
    }

    public void addHealhyCell() {
        
        HealthyCell hc = new HealthyCell();
        cellX = randomThingy.nextInt((int) pane.getWidth());
        cellY = randomThingy.nextInt((int) pane.getHeight());
        hc.setRadius(radius);
        hc.setDx(1);
        hc.setDy(1);
        hc.setCenterX(cellX);
        hc.setCenterY(cellY);
        cellsArrayList.add(hc);
        pane.getChildren().add(hc);
        recenterCells();
        borderSpawnCorrection(hc);
        
    }

    public void addVirusCell() {
        VirusCell vc = new VirusCell();
        cellX = randomThingy.nextInt((int) pane.getWidth());
        cellY = randomThingy.nextInt((int) pane.getHeight());
        vc.setRadius(radius);
        vc.setDx(1);
        vc.setDy(1);
        vc.setCenterX(cellX);
        vc.setCenterY(cellY);
        cellsArrayList.add(vc);
        pane.getChildren().add(vc);
        recenterCells();
        borderSpawnCorrection(vc);
    }

    public void moveBall() {
        for (Cell c : cellsArrayList) {
            if (c.getCenterX() < c.getRadius()
                    || c.getCenterX() > pane.getWidth() - c.getRadius()) {
                c.setDx(c.getDx() * -1);
            }
            //If the ball reaches the bottom or top border make the step negative
            if (c.getCenterY() < c.getRadius()
                    || c.getCenterY() > pane.getHeight() - c.getRadius()) {
                c.setDy(c.getDy() * -1);
            }
            System.out.println(c.getCenterY());
            //Border Correction
            //Top Border


            c.setCenterX(c.getDx() + c.getCenterX());
            c.setCenterY(c.getDy() + c.getCenterY());

        }

    }

    protected boolean collide() {

        for (int i = 0; i < cellsArrayList.size(); i++) {
            for (int j = 0; j < cellsArrayList.size(); j++) {
                if (j != i) {
                    Cell a = cellsArrayList.get(i);
                    Cell b = cellsArrayList.get(j);
                    if (a.intersects(b.getBoundsInLocal())) {
                        a.setDx(a.getDx() * -1);
                        b.setDx(b.getDx() * -1);
                        a.setDy(a.getDy() * -1);
                        b.setDy(b.getDy() * -1);
                        a.setCenterX(a.getDx() + a.getCenterX());
                        a.setCenterY(a.getDy() + a.getCenterY());
                        b.setCenterX(b.getDx() + b.getCenterX());
                        b.setCenterY(b.getDy() + b.getCenterY());
                        //doesnt work all the time

                        if (a instanceof HealthyCell && b instanceof VirusCell) {
                            a.setHitCounter(a.getHitCounter() + 1);
                            if (a.getHitCounter() == convertHitCounter) {
                                healhtyToVirus(a);
                            }
                        }
                        if (b instanceof HealthyCell && a instanceof VirusCell) {
                            b.setHitCounter(b.getHitCounter() + 1);
                            if (b.getHitCounter() == convertHitCounter) {
                                healhtyToVirus(b);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public void healhtyToVirus(Cell c) {
        VirusCell vc = new VirusCell();
        cellX = c.getCenterX();
        cellY = c.getCenterY();
        vc.setRadius(c.getRadius());
        vc.setDx(c.getDx());
        vc.setDy(c.getDy());
        vc.setCenterX(cellX);
        vc.setCenterY(cellY);
        cellsArrayList.remove(c);
        cellsArrayList.add(vc);
        pane.getChildren().add(vc);
        pane.getChildren().remove(c);
        recenterCells();
    }

    private void recenterCells() {
        final Bounds bounds = pane.getLayoutBounds();
        double paneWidth = bounds.getMaxX();
        double paneHeight = bounds.getMaxY();
        for (Cell cell : cellsArrayList) {
            // Make sure the coordinates of each cell are in between the new width and height of the pane.            
            if (cell.getCenterX() > paneWidth) {
                cell.setDx(cell.getDx() * -1);// Change ball move direction
                int randomPosX = randomThingy.nextInt((int) paneWidth);
                cell.setCenterX(cell.getDx() + randomPosX);
            }
            if (cell.getCenterY() > paneHeight) {
                cell.setDy(cell.getDy() * -1);// Change ball move direction                
                int randomPosY = randomThingy.nextInt((int) 200);
                cell.setCenterY(cell.getDy() + randomPosY);
            }
        }
    }
    
    private void borderSpawnCorrection(Cell c ){
       
                   if (c.getCenterY() < c.getRadius()) {
                c.setCenterY(c.getRadius() + 1);
            }
            //Left Border
            if (c.getCenterX() < c.getRadius()) {
                c.setCenterX(c.getRadius() + 1);
            }
            //Right Border
            if (c.getCenterX() + c.getRadius() > pane.getWidth()) {
                c.setCenterX(c.getCenterX() - c.getRadius());
            }
            if (c.getCenterY() + c.getRadius() > pane.getHeight()) {
                c.setCenterY(c.getCenterY() - c.getRadius());
            } 
    }

}
