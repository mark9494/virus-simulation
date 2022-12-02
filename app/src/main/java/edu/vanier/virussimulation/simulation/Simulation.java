package edu.vanier.virussimulation.simulation;

import edu.vanier.virussimulation.controllers.SimulationWindowController;
import edu.vanier.virussimulation.controllers.WelcomeScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Simulation extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //WelcomeScreenController welcomeScreen = new WelcomeScreenController();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/SimulationWindow.fxml"));
        SimulationWindowController swc = new SimulationWindowController();
        loader.setController(swc);
        Pane root = loader.load();
        Scene scene = new Scene(root, 1500.0, 1000.0);
        stage.setScene(scene);
        
        stage.setTitle("Virus_Simulation");
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
