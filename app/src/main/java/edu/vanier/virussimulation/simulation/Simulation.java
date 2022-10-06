package edu.vanier.virussimulation.simulation;


import edu.vanier.virussimulation.controllers.WelcomeScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



public class Simulation extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        WelcomeScreenController welcomeScreen = new WelcomeScreenController();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/WelcomeScreen.fxml"));
        loader.setController(welcomeScreen);
        Pane root = loader.load();
        Scene scene = new Scene(root, 630.0, 364.0);
        stage.setScene(scene);
        
        stage.setTitle("Virus Simulation");
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
       
      
       
            }
    }
