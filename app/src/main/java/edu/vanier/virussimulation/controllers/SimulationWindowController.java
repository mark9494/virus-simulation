/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.virussimulation.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 *
 * @author Mark
 */
public class SimulationWindowController {
    
    
         @FXML
         private Group g;
   
    public SimulationWindowController(){
        
    }
    
  public void handleStart(){
     
     final Circle c = new Circle(10);
     c.setFill(Color.BLUE);
     g.getChildren().addAll(c);
     
     
     final Timeline timeline = new Timeline();
     timeline.setCycleCount(Timeline.INDEFINITE);
     
     final KeyValue keyValue1 = new KeyValue(c.centerXProperty(),620);
     final KeyValue keyValue2 = new KeyValue(c.centerYProperty(),200);
     
     
    final KeyFrame firstKeyFrame = new KeyFrame(Duration.millis(2000),keyValue1);
    final KeyFrame secondKeyFrame = new KeyFrame(Duration.millis(2000),keyValue2);
    timeline.getKeyFrames().add(firstKeyFrame);
     timeline.getKeyFrames().add(secondKeyFrame);
     timeline.play();
     
  }
    
}
