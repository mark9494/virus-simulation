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
    private Button start;
    @FXML
    private Button stop;
    @FXML
    private Button reset;
    @FXML
    private Pane pane;
    @FXML
    private ImageView iv;
    int numberOfCells;
    HealthyCell circle;

    private Random randomThingy = new Random();

    Timeline timeline;
    protected ArrayList<Cell> cellsArrayList = new ArrayList<Cell>();

    public SimulationWindowController() {

    }

    @FXML
    public void initialize() {
        stop.setDisable(true);
        reset.setDisable(true);
        int numberOfCells = 25;
        for (int i = 0; i < numberOfCells; i++) {
            Cell newCell = new HealthyCell();
            newCell.setRadius(10);
            newCell.setCenterX(0);
            newCell.setCenterY(80);
            newCell.setFill(Color.BLUE);
            newCell.setDx(randomThingy.nextDouble()*10);
            newCell.setDy(randomThingy.nextDouble()*10);
            cellsArrayList.add(newCell);
            System.out.println("new dx  " + newCell.getDx());
        }
        double animationDuration = 0.02;
        // circle = new HealthyCell();
        // circle.setRadius(10);
        //  circle.setFill(Color.BLUE);
        pane.getChildren().addAll(cellsArrayList);
        //  circle.setCenterX(0);
        //  circle.setCenterY(80);
        //  circle.setDx(randomPositionX1 * 10);
        //  circle.setDy(randomPositionX1 * 10);
        EventHandler<ActionEvent> onFinished = this::handleUpdateAnimation;
        timeline = new Timeline(new KeyFrame(Duration.seconds(animationDuration), onFinished));
        //KeyValue key1 = new KeyValue(circle.translateXProperty(), 510d, Interpolator.LINEAR);
        // KeyFrame f = new KeyFrame(Duration.seconds(0.5), key1);
        //timeline.getKeyFrames().add(f);

        //-- Animation configuration.
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);

        //randomPositionX1 = randomThingy.nextDouble(pane.getWidth());
        System.out.println(pane.getWidth());
    }

    public void handleStart() {

        stop.setDisable(false);
        reset.setDisable(false);
        start.setDisable(true);

        timeline.play();
    }

    public void handleStop() {
        reset.setDisable(false);
        stop.setDisable(true);
        start.setDisable(false);
        timeline.pause();
    }

    public void handleReset() {
        reset.setDisable(true);
        stop.setDisable(false);
        start.setDisable(false);
        timeline.stop();
    }

    private void handleUpdateAnimation(ActionEvent event) {
        System.out.println("sasdasd");
        for (Cell c : cellsArrayList) {
            c.setLayoutX(c.getLayoutX() + (c.getDx()));
            c.setLayoutY(c.getLayoutY() + (c.getDy()));
        }
        // circle.setLayoutX(circle.getLayoutX() + circle.getDx());
        // circle.setLayoutY(circle.getLayoutY() + circle.getDy());
        System.out.println(pane.getWidth());
        System.out.println(pane.getHeight());
        moveBall();

    }

    public void moveBall() {
        for (Cell c : cellsArrayList) {
            if (c.getLayoutX() <= (pane.getMinWidth())
                    || c.getLayoutX() >= (pane.getWidth() - 20)) {
                c.setDx(c.getDx() * -1);
            }

            //If the ball reaches the bottom or top border make the step negative
            if ((c.getLayoutY() >= ((pane.getHeight() - 102)))
                    || (c.getLayoutY() <= ((-65)))) {
                c.setDy(c.getDy() * -1);
            }
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

