/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.virussimulation.controllers;

import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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

    @FXML
    private Pane pane;
    @FXML
    private ImageView iv;
    
    Circle circle;
    
    private Random randomThingy = new Random();
    
    Timeline timeline;
   
    public SimulationWindowController() {

    }

    public void handleStart() {
        
        double animationDuration = 0.1;
        circle = new Circle(10);
        circle.setFill(Color.BLUE);
        pane.getChildren().addAll(circle);
        circle.setCenterX(0);
        circle.setCenterY(80);
        double randomPositionX = randomThingy.nextDouble(pane.getWidth());
        
        EventHandler<ActionEvent> onFinished = this::handleUpdateAnimation;
        timeline = new Timeline(new KeyFrame(Duration.seconds(animationDuration), onFinished));
        //KeyValue key1 = new KeyValue(circle.translateXProperty(), 510d, Interpolator.LINEAR);
       // KeyFrame f = new KeyFrame(Duration.seconds(0.5), key1);
        //timeline.getKeyFrames().add(f);

        //-- Animation configuration.
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();

       
    }

    private void handleUpdateAnimation(ActionEvent event) {
        //TODO: update the position of the circle.
        // TODO: loop through the list of cicrlce and update their properties.
        // The max X position should not exceed the pane's width pane.getWidth()
       
        System.out.println("111");
        System.out.println("WIDTH :::::" + pane.getWidth());
        double randomPositionX = randomThingy.nextDouble(510d);
        circle.setTranslateX(randomPositionX);
        System.out.println(circle.getTranslateX());
        if (circle.intersects(pane.getBoundsInParent())) {
            System.out.println(circle.getCenterX() + " ");
            System.out.println("collision");
            circle.setFill(Color.RED);
            circle.setTranslateX(620);
            //circle.setTranslateY(620);
            
        }
        System.out.println("Animation loop: Hi ");
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
}
