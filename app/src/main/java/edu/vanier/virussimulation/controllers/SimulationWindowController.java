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

    private int numberOfCells = 15;
    private double currentRate = 10;
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
        generateCells();
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
            cellX = randomThingy.nextInt(400);
            cellY = randomThingy.nextInt(400);
            Cell newCell = new HealthyCell();
            newCell.setRadius(radius);
            newCell.setCenterX(cellX);
            newCell.setCenterY(cellY);
            newCell.setDx(1);
            newCell.setDy(1);
            cellsArrayList.add(newCell);
            pane.getChildren().addAll(newCell);
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
        //FIX SPAWN (CAN SPAWN OUTSIDE)
        //TODO: check the random starting position of cells.
        //FIXME:
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
            if (c.getCenterY() < 14) {
                c.setCenterY(14);
            }
            if (c.getCenterX() < 14) {
                c.setCenterX(14);
            }
            if (c.getCenterY() > pane.getHeight() - 14) {
                c.setCenterY(c.getCenterY() - 13);
            }
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
                        if (a instanceof HealthyCell && b instanceof VirusCell || b instanceof HealthyCell && a instanceof VirusCell) {
                            a.setFill(Color.RED);

                        }
                    }
                }
            }
        }
        return false;
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
}
