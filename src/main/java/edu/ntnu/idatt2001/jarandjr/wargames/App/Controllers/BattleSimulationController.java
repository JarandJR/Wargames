package edu.ntnu.idatt2001.jarandjr.wargames.App.Controllers;

import edu.ntnu.idatt2001.jarandjr.wargames.App.Simulation;
import edu.ntnu.idatt2001.jarandjr.wargames.FileManagement;
import edu.ntnu.idatt2001.jarandjr.wargames.ENUMS.TERRAIN;
import edu.ntnu.idatt2001.jarandjr.wargames.App.AlertBox;
import edu.ntnu.idatt2001.jarandjr.wargames.Battle;
import javafx.scene.control.Button;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.fxml.FXML;

import java.util.ResourceBundle;
import java.io.IOException;
import java.util.Objects;
import java.net.URL;

/**
 * The controller for the battle simulator page
 */
public class BattleSimulationController implements Initializable {

    @FXML
    public Label txtTitleArmyOne;
    public Label txtTitleArmyTwo;
    public Button btnSimulate;
    public Canvas terrain;
    public Canvas canvas;

    boolean battled;
    Simulation sim;
    Battle battle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        battled = false;
        try{
           battle = FileManagement.readBattleInfoFromFile();
        }catch (IOException e) {
            System.out.println(e.getMessage());
            AlertBox.alert("Error", "Something went wrong while trying to load battleFiles. Try again");
        }
        sim = new Simulation(canvas, battle);
        txtTitleArmyOne.setText(battle.getArmyOne().getName());
        txtTitleArmyTwo.setText(battle.getArmyTwo().getName());
        terrain.getGraphicsContext2D().drawImage(getImage(),0,0, terrain.getWidth(), terrain.getHeight());
    }


    /**
     * The method for getting the terrain image
     * @return the image depending on th terrain picked for the battle
     */
    private Image getImage(){
        if (battle.getTerrain().equals(TERRAIN.PLAINS))
            return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Plains.jpg")));
        else if (battle.getTerrain().equals(TERRAIN.HILL))
            return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/hills.jpg")));
        else
            return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/foggyForrest.jpg")));
    }

    /**
     * The method being called when the start battle button is clicked
     * If the battle has already been simulated, the battle class gets resat
     * Then the simulation is made and started
     */
    public void btnStartBattle() {
        if (battled) {
            try {
                battle = FileManagement.readBattleInfoFromFile();
                sim = new Simulation(canvas, battle);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                AlertBox.alert("Error", "Something went wrong while trying to load battleFiles. Try again");
            }
        }
        btnSimulate.setDisable(true);
        sim.simulate(btnSimulate);
        battled = true;
    }

    /**
     * The method for switching to the front page
     * @param event the event handled
     */
    public void btnSwitchToStartUpPage(ActionEvent event) {
        try {
            switchScene("/FXML/StartUpPage.fxml", event);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            AlertBox.alert("Error", "Something went wrong while trying to load page.");
        }
    }

    /**
     * The method for switching to the game setup page
     * @param event the event handled
     */
    public void btnSwitchPageToGameSetup(ActionEvent event) {
        try {
            switchScene("/FXML/GameSetupPage.fxml", event);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            AlertBox.alert("Error", "Something went wrong while trying to load page.");
        }
    }

    /**
     * The private method for switching scene
     * @param path to the FXML file
     * @param event the event
     * @throws IOException when the method can't find the file
     */
    private void switchScene(String path, ActionEvent event)throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        String css = Objects.requireNonNull(this.getClass().getResource("/Styles/WargamesStyleSheet.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
