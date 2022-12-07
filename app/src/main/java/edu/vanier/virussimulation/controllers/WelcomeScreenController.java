/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.virussimulation.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author 2125881
 */
public class WelcomeScreenController {

    /**
     * Fxml function that controls the start simulation button.
     *
     */
    @FXML
    public void handleSimulationButton() throws IOException {
        Stage secondWindow = new Stage();

        SimulationWindowController window2 = new SimulationWindowController();

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/SimulationWindow.fxml"));
        loader.setController(window2);
        Pane root = loader.load();
        Scene scene = new Scene(root, 1200.0, 1000.0);
        secondWindow.setScene(scene);

        secondWindow.setTitle("Virus Simulation");
        secondWindow.sizeToScene();
        secondWindow.show();
    }
}
