/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.vanier.virussimulation.controllers;

import edu.vanier.virussimulation.cells.Cell;
import edu.vanier.virussimulation.cells.CovidVirus;
import edu.vanier.virussimulation.cells.FluVirus;
import edu.vanier.virussimulation.cells.HealthyCell;
import edu.vanier.virussimulation.cells.VirusCell;
import edu.vanier.virussimulation.settings.SimulationSettings;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.apache.commons.lang3.time.StopWatch;

/**
 *
 * @author Mark
 */
public class SimulationWindowController extends SimulationSettings {

    public double randomPositionX1;
    private int startBtnCounter = 0;

    @FXML
    private Button btnStart;
    @FXML
    private Button btnAddFluVirus;
    @FXML
    private Button btnAddCovidVirus;
    @FXML
    private Button btnPause;
    @FXML
    private Button btnReset;
    @FXML
    private Slider sldAmountCell;
    @FXML
    private Slider sldCellSize;
    @FXML
    private Slider sldCellSpeed;
    @FXML
    private Slider sldAmountVirus;
    @FXML
    private Slider sldSizeVirus;
    @FXML
    private Slider sldSpeedVirus;
    @FXML
    private Pane pane;
    @FXML
    private Button btnCustomVirus;
    @FXML
    private Slider sldInfectionRate;
    @FXML
    private ToggleButton tglResetSlider;
    @FXML
    private Button btnSubmit;
    @FXML
    private VBox vBox;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Label txtCurrentTime;
    @FXML
    private Label nbHCells;
    @FXML
    private Label nbVCells;
    @FXML
    private Label percHCells;
    @FXML
    private Label percVCells;

    boolean clickedCovidButton = false;
    boolean clickedFluButton = false;
    boolean clickedCustomButton = false;

    public static StopWatch sw;

    SimulationSettings simSettings;

    final private Random randomThingy = new Random();

    public static Timeline timeline;
    protected ArrayList<Cell> cellsArrayList = new ArrayList<Cell>();

    @FXML
    public void initialize() {
        disableControlButtons(true, true, true, false);
        simSettings = new SimulationSettings();
        sw = new StopWatch();
        sw.start();
        sw.suspend();
        createAnimation();
        direction.add(-1);
        direction.add(1);
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            recenterCells();
        });
        pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            recenterCells();
        });
    }

    /**
     * Creates number of cells based off of slider info from user
     *
     */
    public void generateCells() {
        numberOfCells = (int) sldAmountCell.getValue();
        for (int i = 0; i < numberOfCells; i++) {
            addHealhyCell();
        }
    }

    /**
     * method to control the activation of control buttons throughout the
     * simulation
     *
     *
     * @param play - On/Off.
     * @param pause - On/off.
     * @param stop - On/off.
     * @param submit - On/off.
     */
    private void disableControlButtons(boolean play, boolean pause, boolean stop, boolean submit) {
        btnStart.setDisable(play);
        btnPause.setDisable(pause);
        btnReset.setDisable(stop);
        btnSubmit.setDisable(submit);
    }

    /**
     * controls the activation of the virus type.
     *
     *
     * @param covid - On/Off.
     * @param flu - On/off.
     * @param custom - On/off.
     */
    public void disableVirusButtons(boolean covid, boolean flu, boolean custom) {
        btnCustomVirus.setDisable(custom);
        btnAddCovidVirus.setDisable(covid);
        btnAddFluVirus.setDisable(flu);

    }

    /**
     * controls the activation of the virus customization sliders.
     *
     *
     * @param amount - On/Off.
     * @param size - On/off.
     * @param speed - On/off.
     * @param infection- On/off.
     */
    public void disableVirusSliders(boolean amount, boolean size, boolean speed, boolean infection) {
        sldAmountVirus.setDisable(amount);
        sldSizeVirus.setDisable(size);
        sldSpeedVirus.setDisable(speed);
        sldInfectionRate.setDisable(infection);
    }

    /**
     * Resets all the values of the slider to zero to pick new values.
     *
     */
    public void setSlidersDefault() {
        sldInfectionRate.setValue(0);
        sldAmountVirus.setValue(0);
        sldSpeedVirus.setValue(0);
        sldSizeVirus.setValue(0);
        sldAmountCell.setValue(0);
        sldCellSize.setValue(0);
        sldCellSpeed.setValue(0);
    }

    /**
     * Creating the timeline and settings.
     *
     */
    public void createAnimation() {
        timeline = new Timeline(
                new KeyFrame(Duration.millis(animationDuration), e -> handleUpdateAnimation()));
        timeline.setRate(currentRate);
        timeline.setCycleCount(Timeline.INDEFINITE);

    }

    /**
     * Detects when there are no more virus cells and ends the simulation.
     *
     */
    public void endSimulation() {
        boolean isHealthyLeft = false;
        for (Cell c : cellsArrayList) {
            if (c instanceof HealthyCell) {
                isHealthyLeft = true;
            }
        }
        if (!isHealthyLeft) {
            timeline.stop();
            sw.suspend();

            System.out.println("Full Animation Time " + sw.getTime(TimeUnit.SECONDS) + " Seconds.");
            disableControlButtons(true, true, false, false);
        }
    }

    /**
     * Fxml function that controls the start button.
     *
     */
    @FXML
    public void handleStart() {
        disableControlButtons(true, false, false, true);
        timeline.play();
        vBox.setDisable(true);

        sw.resume();

        startBtnCounter++;

    }

    /**
     * Fxml function that controls the stop button.
     *
     */
    @FXML
    public void handleStop() {
        disableControlButtons(false, true, false, true);
        timeline.pause();
        sw.suspend();
    }

    /**
     * Fxml function that controls the reset button.
     *
     */
    @FXML
    public void handleReset() {
        clickedCustomButton = false;
        clickedFluButton = false;
        clickedCovidButton = false;
        vBox.setDisable(false);

        for (Cell c : cellsArrayList) {
            pane.getChildren().remove(c);
        }
        if (tglResetSlider.isSelected()) {
            setSlidersDefault();
        }
        txtCurrentTime.setText("");
        colorPicker.setDisable(false);
        cellsArrayList.removeAll(cellsArrayList);
        disableControlButtons(true, true, true, false);
        disableVirusButtons(false, false, false);
        disableVirusSliders(false, false, false, false);
        timeline.stop();

        sw.reset();
        sw.start();
        sw.suspend();// we need all these methods called, otherwise the stopwatch won't work
        statsCounter();
    }

    /**
     * Fxml function that controls the submit button.
     *
     */
    public void handleSubmit() {
        if (!clickedCovidButton && !clickedFluButton && !clickedCustomButton) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("You did not choose a virus.");
            a.showAndWait();
            return;
        }
        if (!cellsArrayList.isEmpty()) {
            for (Cell c : cellsArrayList) {
                pane.getChildren().remove(c);
            }
            cellsArrayList.removeAll(cellsArrayList);
        }
        disableControlButtons(false, true, true, true);
        radius = (int) sldCellSize.getValue();
        healthyDx = (int) sldCellSpeed.getValue() * direction.get(rand.nextInt(2));
        healthyDy = (int) sldCellSpeed.getValue() * direction.get(rand.nextInt(2));

        if (clickedCustomButton) {
            convertHitCounter = (int) sldInfectionRate.getValue();
            amountOfVirus = (int) sldAmountVirus.getValue();
            virusRadius = (int) sldSizeVirus.getValue();
            virusDx = (int) sldSpeedVirus.getValue();
            virusDy = (int) sldSpeedVirus.getValue();
            virusColor = colorPicker.getValue();
            addCustomVirusCell(virusRadius, virusDx, virusDy, amountOfVirus, virusColor);
        }
        if (clickedCovidButton) {
            int numberOfVirus = (int) sldAmountVirus.getValue();
            createCovidVirus(numberOfVirus);
        }
        if (clickedFluButton) {
            int numberOfVirus = (int) sldAmountVirus.getValue();
            createFluVirus(numberOfVirus);
        }
        generateCells();
    }

    /**
     * Fxml function that controls the Covid virus button.
     *
     */
    @FXML
    public void handleAddCovidVirus() {
        clickedCovidButton = true;
        disableVirusButtons(false, true, true);
        disableVirusSliders(false, true, true, true);
        colorPicker.setDisable(true);
    }

    /**
     * Fxml function that controls the custom virus button.
     *
     */
    @FXML
    public void handleAddCustomVirus() {
        clickedCustomButton = true;
        disableVirusButtons(true, true, false);
    }

    /**
     * Fxml function that controls the flu virus button.
     *
     */
    @FXML
    public void handleAddFluVirus() {
        clickedFluButton = true;
        disableVirusButtons(true, false, true);
        disableVirusSliders(false, true, true, true);
        colorPicker.setDisable(true);
    }

    /**
     * Method that is looped through the single keyframe animation.
     *
     */
    private void handleUpdateAnimation() {
        moveBall();
        collide();
        endSimulation();
        statsCounter();
        updateTimer();
    }

    /**
     * Creates a healthy cell
     *
     */
    public void addHealhyCell() {

        HealthyCell hc = new HealthyCell();
        cellX = randomThingy.nextInt((int) pane.getWidth());
        cellY = randomThingy.nextInt((int) pane.getHeight());
        hc.setRadius(radius);
        hc.setDx(healthyDx);
        hc.setDy(healthyDy);
        hc.setCenterX(cellX);
        hc.setCenterY(cellY);
        cellsArrayList.add(hc);
        pane.getChildren().add(hc);
        recenterCells();
        borderSpawnCorrection(hc);

    }
//Turn into Custom and change settings based on user.

    /**
     * Creates custom virus based on user's chosen values.
     *
     * @param radius - size of virus.
     * @param dx - horizontal speed.
     * @param dy - vertical speed.
     * @param amount - number of virus cells.
     * @param color - color of virus cell.
     */
    public void addCustomVirusCell(int radius, int dx, int dy, int amount, Color color) {
        for (int i = 0; i < amount; i++) {
            VirusCell vc = new VirusCell();
            cellX = randomThingy.nextInt((int) pane.getWidth());
            cellY = randomThingy.nextInt((int) pane.getHeight());
            vc.setRadius(radius);
            vc.setDx(dx);
            vc.setDy(dy);
            vc.setCenterX(cellX);
            vc.setCenterY(cellY);
            vc.setFill(color);
            cellsArrayList.add(vc);
            pane.getChildren().add(vc);
            recenterCells();
            borderSpawnCorrection(vc);
        }

    }

    /**
     * Creates predetermined covid virus.
     *
     * @param amount - number of covid cells.
     */
    public void createCovidVirus(int amount) {
        for (int i = 0; i < amount; i++) {
            CovidVirus cv = new CovidVirus();
            convertHitCounter = cv.getHitCounter();
            cellX = randomThingy.nextInt((int) pane.getWidth());
            cellY = randomThingy.nextInt((int) pane.getHeight());
            cv.setDx(healthyDx * 2);
            cv.setDy(healthyDy * 2);
            cv.setCenterX(cellX);
            cv.setCenterY(cellY);
            cellsArrayList.add(cv);
            pane.getChildren().add(cv);
            recenterCells();
            borderSpawnCorrection(cv);
        }
    }

    /**
     * Creates predetermined flu virus.
     *
     * @param amount - number of flu cells.
     */
    public void createFluVirus(int amount) {
        for (int i = 0; i < amount; i++) {
            FluVirus fv = new FluVirus();
            convertHitCounter = fv.getHitCounter();
            cellX = randomThingy.nextInt((int) pane.getWidth());
            cellY = randomThingy.nextInt((int) pane.getHeight());
            fv.setDx(healthyDx * 0.5);
            fv.setDy(healthyDy * 0.5);
            fv.setCenterX(cellX);
            fv.setCenterY(cellY);
            cellsArrayList.add(fv);
            pane.getChildren().add(fv);
            recenterCells();
            borderSpawnCorrection(fv);
        }
    }

    /**
     * Takes care of the ball's movement relative with the border of the
     * simulation.
     *
     */
    public void moveBall() {
        for (Cell c : cellsArrayList) {
            if (c.getCenterX() < c.getRadius() || c.getCenterX() > pane.getWidth() - c.getRadius()) {
                c.setDx(c.getDx() * -1);
            }
            //If the ball reaches the bottom or top border make the step negative
            if (c.getCenterY() < c.getRadius() || c.getCenterY() > pane.getHeight() - c.getRadius()) {
                c.setDy(c.getDy() * -1);
            }
            //Border Correction
            //Top Border
            c.setCenterX(c.getDx() + c.getCenterX());
            c.setCenterY(c.getDy() + c.getCenterY());

        }

    }

    /**
     * Manages the collision between two cells. Also manages the type of
     * collision (healthy - virus).
     *
     */
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

                        if (a instanceof HealthyCell && b instanceof VirusCell) {
                            a.setHitCounter(a.getHitCounter() + 1);
                            if (a.getHitCounter() == convertHitCounter) {
                                healhtyToVirus(a, (VirusCell) b);
                            }
                        }
                        if (b instanceof HealthyCell && a instanceof VirusCell) {
                            b.setHitCounter(b.getHitCounter() + 1);
                            if (b.getHitCounter() == convertHitCounter) {
                                healhtyToVirus(b, (VirusCell) a);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Manages the conversion of a healthy cell to a virus cell based off of a
     * collision.
     *
     * @param c - taken healthy cell.
     * @param a- taken virus cell.
     */
    public void healhtyToVirus(Cell c, VirusCell a) {
        if (a instanceof CovidVirus) {
            CovidVirus vc = new CovidVirus();
            cellX = c.getCenterX();
            cellY = c.getCenterY();
            vc.setDx(healthyDx * 2);
            vc.setDy(healthyDy * 2);
            vc.setCenterX(cellX);
            vc.setCenterY(cellY);
            cellsArrayList.remove(c);
            cellsArrayList.add(vc);
            pane.getChildren().add(vc);
            pane.getChildren().remove(c);
            recenterCells();
        } else if (a instanceof FluVirus) {
            FluVirus vc = new FluVirus();
            cellX = c.getCenterX();
            cellY = c.getCenterY();
            vc.setDx(healthyDx * 0.5);
            vc.setDy(healthyDy * 0.5);
            vc.setCenterX(cellX);
            vc.setCenterY(cellY);
            cellsArrayList.remove(c);
            cellsArrayList.add(vc);
            pane.getChildren().add(vc);
            pane.getChildren().remove(c);
            recenterCells();
        } else {
            VirusCell vc = new VirusCell();
            vc.setCenterX(c.getCenterX());
            vc.setCenterY(c.getCenterY());
            vc.setRadius(virusRadius);
            vc.setDx(a.getDx());
            System.out.println(virusDx);
            vc.setDy(a.getDy());
            System.out.println(virusDy);
            vc.setFill(virusColor);
            cellsArrayList.add(vc);
            pane.getChildren().add(vc);
            recenterCells();
            borderSpawnCorrection(vc);
            cellsArrayList.remove(c);
            pane.getChildren().remove(c);
            recenterCells();
        }
    }

    /**
     * Corrects problem where cells do not spawn inside simulation border
     *
     */
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

    /**
     * Corrects problem where cells get stuck and move parallel to simulation
     * border.
     *
     * @param c - taken cell inside simulation.
     */
    private void borderSpawnCorrection(Cell c) {

        if (c.getCenterY() < c.getRadius()) {
            c.setCenterY(c.getRadius() + 1);
        }
        //Left Border
        if (c.getCenterX() < c.getRadius()) {
            c.setCenterX(c.getRadius() + 1);
        }
        //Right Border
        if (c.getCenterX() + c.getRadius() > pane.getWidth()) {
            c.setCenterX(c.getCenterX() - c.getRadius());
        }
        if (c.getCenterY() + c.getRadius() > pane.getHeight()) {
            c.setCenterY(c.getCenterY() - c.getRadius());
        }
    }

    final ArrayList HCellsList = new ArrayList();
    final ArrayList VCellsList = new ArrayList();

    /**
     * Method which updates the statistical data of the simulation. Shows
     * healthy cells remaining, virus cells remaining and percentages.
     */
    private void statsCounter() {
        for (Cell c : cellsArrayList) {
            if (c instanceof HealthyCell) {
                this.HCellsList.add(c);
            } else if (c instanceof VirusCell) {
                this.VCellsList.add(c);
            }
        }
        double healthyArraySize = this.HCellsList.size();
        double cellsArraySize = this.cellsArrayList.size();
        double virusArraySize = this.VCellsList.size();

        nbHCells.setText(
                (int) healthyArraySize
                + "");
        nbVCells.setText(
                (int) virusArraySize + "");
        //Healthy cell percentage
        double healthyPercentage = 0;

        if (!this.cellsArrayList.isEmpty()) {
            healthyPercentage = (healthyArraySize / cellsArraySize) * 100;
        }
        percHCells.setText((int) healthyPercentage + "%");
        //Virus Percentage
        double virusPercentage = 0;

        if (!this.cellsArrayList.isEmpty()) {
            virusPercentage = (virusArraySize / cellsArraySize * 100);
        }

        percVCells.setText(
                (int) virusPercentage + "%");

        this.HCellsList.removeAll(
                this.HCellsList);

        this.VCellsList.removeAll(
                this.VCellsList);

    }

    /**
     * Changes the text displayed in the stopwatch part of the simulation by
     * always updating the text field with the time.
     *
     */
    private void updateTimer() {
        long miliseconds = (sw.getTime(TimeUnit.MILLISECONDS)) - ((sw.getTime(TimeUnit.SECONDS)) * 1000);
        txtCurrentTime.setText(sw.getTime(TimeUnit.MINUTES) + " Minutes " + sw.getTime(TimeUnit.SECONDS) + " Seconds " + miliseconds + " miliseconds");
    }
}
