/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.virussimulation.controllers;

import edu.vanier.virussimulation.cells.HealthyCells;
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
    private Pane pane;
    @FXML
    private ImageView iv;

    HealthyCells circle;

    private Random randomThingy = new Random();

    Timeline timeline;

    public SimulationWindowController() {

    }
    double dx = 30;
    double dy = 30;

    public void handleStart() {
        double animationDuration = 0.02;
        circle = new HealthyCells();
        circle.setRadius(10);
        circle.setFill(Color.BLUE);
        pane.getChildren().addAll(circle);
        circle.setCenterX(0);
        circle.setCenterY(80);
        circle.setDx(randomPositionX1);
        circle.setDy(randomPositionX1);
        EventHandler<ActionEvent> onFinished = this::handleUpdateAnimation;
        timeline = new Timeline(new KeyFrame(Duration.seconds(animationDuration), onFinished));
        //KeyValue key1 = new KeyValue(circle.translateXProperty(), 510d, Interpolator.LINEAR);
        // KeyFrame f = new KeyFrame(Duration.seconds(0.5), key1);
        //timeline.getKeyFrames().add(f);

        //-- Animation configuration.
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
        randomPositionX1 = randomThingy.nextDouble(pane.getWidth());
        System.out.println(pane.getWidth());
    }

    private void handleUpdateAnimation(ActionEvent event) {
        System.out.println("sasdasd");
        circle.setLayoutX(circle.getLayoutX() + dx);
        circle.setLayoutY(circle.getLayoutY() + dy);
        Bounds bounds = pane.getBoundsInLocal();
        System.out.println(pane.getWidth());
        System.out.println(pane.getHeight());
        System.out.println(circle.getLayoutY());
        if (circle.getLayoutX() <= (pane.getMinWidth() + circle.getRadius())
                || circle.getLayoutX() >= (pane.getWidth() - circle.getRadius())) {
            dx *= -1;
        }

        //If the ball reaches the bottom or top border make the step negative
        if ((circle.getLayoutY() >= ((pane.getHeight()-102) - circle.getRadius()))
                || (circle.getLayoutY() <= ((-50) + circle.getRadius()))) {
            dy *= -1;

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

