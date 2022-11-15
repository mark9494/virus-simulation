/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.virussimulation.controllers;

import edu.vanier.virussimulation.cells.Cell;
import edu.vanier.virussimulation.cells.HealthyCell;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
        //KeyValue key1 = new KeyValue(circle.translateXProperty(), 510d, Interpolator.LINEAR);
        // KeyFrame f = new KeyFrame(Duration.seconds(0.5), key1);
        //timeline.getKeyFrames().add(f);
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
            newCell.setFill(Color.BLUE);
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
    }

    public void addHealhyCell() {
        //FIX SPAWN (CAN SPAWN OUTSIDE)
        //TODO: check the random starting position of cells.
        //FIXME:
        HealthyCell hc = new HealthyCell();
        cellX = randomThingy.nextInt((int)pane.getWidth());
        cellY = randomThingy.nextInt((int)pane.getHeight());
        hc.setRadius(radius);
        hc.setDx(1);
        hc.setDy(1);
        hc.setCenterX(cellX);
        hc.setCenterY(cellY);
        hc.setFill(Color.BLUE);
        cellsArrayList.add(hc);
        pane.getChildren().add(hc);
        recenterCells();
    }

    public void addVirusCell() {
        HealthyCell vc = new HealthyCell();
        cellX = randomThingy.nextInt((int)pane.getWidth());
        cellY = randomThingy.nextInt((int)pane.getHeight());
        vc.setRadius(radius);
        vc.setDx(1);
        vc.setDy(1);
        vc.setCenterX(cellX);
        vc.setCenterY(cellY);
        vc.setFill(Color.RED);
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

    //TODO: update the position of the circle.
    // TODO: loop through the list of cicrlce and update their properties.
    // The max X position should not exceed the pane's width pane.getWidth()
    //System.out.println("111");
    //System.out.println("WIDTH :::::" + pane.getWidth());
    /*double randomPositionX = randomThingy.nextDouble(pane.getWidth());
       
        circle.setTranslateX(710);
        System.out.println(circle.getTranslateX());
        if (circle.intersects(pane.getBoundsInLocal())) {
            System.out.println(circle.getCenterX() + " ");
            System.out.println("collision");
            circle.setFill(Color.RED);
            circle.setTranslateX(randomPositionX);
            
        }
        System.out.println("Animation loop: Hi ");*/
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

//    private void handleUpdateAnimation(ActionEvent event) {
//        //TODO: update the position of the circle.
//        // TODO: loop through the list of cicrlce and update their properties.
//        // The max X position should not exceed the pane's width pane.getWidth()
//        System.out.println("111");
//        double randomPositionX = randomThingy.nextDouble(pane.getWidth());
//        circle.setTranslateX(randomPositionX);
//        System.out.println(circle.getTranslateX());
//        if( circle.getCenterX() == pane.getWidth()) {
//            System.out.println(circle.getCenterX() + " ");
//            System.out.println("collision");
//            circle.setFill(Color.RED);
//            circle.setTranslateX(620);
//            //circle.setTranslateY(620);
//            
//        }
//        System.out.println("Animation loop: Hi ");
//    }

