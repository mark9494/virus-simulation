/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.virussimulation.controllers;

import edu.vanier.virussimulation.cells.Cell;
import edu.vanier.virussimulation.cells.CovidVirus;
import edu.vanier.virussimulation.cells.HealthyCell;
import edu.vanier.virussimulation.cells.VirusCell;
import edu.vanier.virussimulation.settings.SimulationSettings;
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
public class SimulationWindowController extends SimulationSettings {

    public double randomPositionX1;
    private int startBtnCounter = 0;
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

    final private Random randomThingy = new Random();

    Timeline timeline;
    protected ArrayList<Cell> cellsArrayList = new ArrayList<Cell>();

    public SimulationWindowController() {

    }

    @FXML
    public void initialize() {
        createAnimation();
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
            addHealhyCell();
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
    public void endSimulation(){
        boolean isHealthyLeft = false;
        for(Cell c : cellsArrayList){
            if(c instanceof HealthyCell){
                isHealthyLeft = true;
            }
            
        }
        if(!isHealthyLeft){
            timeline.stop();
            disableControlButtons(true, true, false);
        }
    }

    public void handleStart() {
        if (startBtnCounter == 0) {
            generateCells();
        }
        disableControlButtons(true, false, false);
        timeline.play();
        startBtnCounter++;

    }

    public void handleStop() {
        disableControlButtons(false, true, false);
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
        //addVirusCell();
        createCovidVirus();
    }

    public void handleAddCell() {
        addHealhyCell();
    }

    private void handleUpdateAnimation() {
        moveBall();
        collide();
        endSimulation();
    }

    public void handleSpawnOverlap(Cell newCell) {
        for (Cell c : cellsArrayList) {
        }
//        for (Cell a : cellsArrayList) {
//            for (Cell b : cellsArrayList) {
//                if (a.getCenterX() > b.getCenterX() && a.getCenterY() > b.getCenterY()) {
//                    if ((a.getCenterX() - b.getCenterX()) < 2 * radius) {
//                        if (a.getCenterY() > b.getCenterY()) {
//                            if (a.getCenterY() - b.getCenterY() < 2 * radius) {
//                                a.setFill(Color.BLACK);
//                            }
//                        }
//                    }
//                }
//                if (b.getCenterX() > a.getCenterX()) {
//                    if ((b.getCenterX() - a.getCenterX()) < 2 * radius) {
//                        if (b.getCenterY() > a.getCenterY()) {
//                            if (b.getCenterY() - a.getCenterY() < 2 * radius) {
//                                b.setFill(Color.BLACK);
//
//                            }
//                        }
//
//                    }
//                }
//            }
//        }
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
        handleSpawnOverlap(hc);
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
        vc.setDx(healthyDx);
        vc.setDy(healthyDy);
        vc.setCenterX(cellX);
        vc.setCenterY(cellY);
        cellsArrayList.add(vc);
        pane.getChildren().add(vc);
        recenterCells();
        borderSpawnCorrection(vc);
    }

    //Double the speed of healthy Cells
    //Healhty needs to be hit 2 times to 
    public void createCovidVirus() {
        CovidVirus cv = new CovidVirus();
        convertHitCounter = cv.getHitCounter();
        cellX = randomThingy.nextInt((int) pane.getWidth());
        cellY = randomThingy.nextInt((int) pane.getHeight());
        cv.setDx(healthyDx * 2);
        cv.setDy(healthyDy * 2);
        cv.setCenterX(cellX);
        cv.setCenterY(cellY);
        cellsArrayList.add(cv);
        pane.getChildren().add(cv);
        recenterCells();
        borderSpawnCorrection(cv);
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
                                healhtyToVirus(a, (VirusCell) b);
                            }
                        }
                        if (b instanceof HealthyCell && a instanceof VirusCell) {
                            b.setHitCounter(b.getHitCounter() + 1);
                            if (b.getHitCounter() == convertHitCounter) {
                                healhtyToVirus(b, (VirusCell) a);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public void healhtyToVirus(Cell c, VirusCell a) {
        if (a instanceof CovidVirus) {
            CovidVirus vc = new CovidVirus();
            cellX = c.getCenterX();
            cellY = c.getCenterY();
            vc.setDx(healthyDx * 2);
            vc.setDy(healthyDy * 2);
            vc.setCenterX(cellX);
            vc.setCenterY(cellY);
            cellsArrayList.remove(c);
            cellsArrayList.add(vc);
            pane.getChildren().add(vc);
            pane.getChildren().remove(c);
            recenterCells();
        }
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

    private void borderSpawnCorrection(Cell c) {

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
