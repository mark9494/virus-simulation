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
         Circle circle;
    public SimulationWindowController(){
        
    }
    
  public void handleStart(){
     
     circle = new Circle(10);
     circle.setFill(Color.BLUE);
     pane.getChildren().addAll(circle);
     
     
     final Timeline timeline = new Timeline();
     timeline.setCycleCount(Timeline.INDEFINITE);
     
     final KeyValue keyValue1 = new KeyValue(circle.centerXProperty(),620);
     final KeyValue keyValue2 = new KeyValue(circle.centerYProperty(),200);
     
    
    final KeyFrame firstKeyFrame = new KeyFrame(Duration.millis(2000),keyValue1);
    final KeyFrame secondKeyFrame = new KeyFrame(Duration.millis(2000),keyValue2);
    timeline.getKeyFrames().add(firstKeyFrame);
     timeline.getKeyFrames().add(secondKeyFrame);
     timeline.play();
     
//     if(c.getBoundsInParent().intersects(g.getBoundsInParent())){
//           System.out.println("collision");   
//       } 
     
       
     
  }
  
    
}
