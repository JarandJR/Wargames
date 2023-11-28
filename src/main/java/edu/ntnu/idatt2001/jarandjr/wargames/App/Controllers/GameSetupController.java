package edu.ntnu.idatt2001.jarandjr.wargames.App.Controllers;

import edu.ntnu.idatt2001.jarandjr.wargames.FileManagement;
import edu.ntnu.idatt2001.jarandjr.wargames.ENUMS.TERRAIN;
import edu.ntnu.idatt2001.jarandjr.wargames.App.AlertBox;
import edu.ntnu.idatt2001.jarandjr.wargames.Battle;
import edu.ntnu.idatt2001.jarandjr.wargames.Army;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.canvas.Canvas;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXML;

import java.util.ResourceBundle;
import java.io.IOException;
import java.util.Objects;
import java.util.List;
import java.net.URL;

/**
 * The controller for the game setup page
 */
public class GameSetupController implements Initializable {

    @FXML
    public ListView<String> listArmyTwoSetUp;
    public ListView<String> listArmyOneSetUp;
    public ComboBox<String> cBoxTerrain;
    public ComboBox<String> cBoxArmyOne;
    public ComboBox<String> cBoxArmyTwo;
    public Label txtCurrentArmyOne;
    public Label txtCurrentArmyTwo;
    public Canvas terrainCanvas;

    List<Army> armies;
    String terrain;
    Army armyOne;
    Army armyTwo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        terrainCanvas.getGraphicsContext2D().drawImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/foggyWinterForrest.jpg"))), 0, 0, terrainCanvas.getWidth(), terrainCanvas.getHeight());
        try{
            armies = FileManagement.readListOfArmiesFromFile();
        }catch (IOException e) {
            System.out.println(e.getMessage());
            AlertBox.alert("Error", "Something went wrong while trying to load files. Try again");
        }
        listCBoxes();
    }

    /**
     * The method for listing the comboBoxes and adding a listener to the army comboBoxes
     */
    private void listCBoxes() {
        listCBoxArmy(cBoxArmyOne);
        cBoxArmyOne.getSelectionModel().selectedItemProperty().addListener((observableValue) -> setArmyOne(cBoxArmyOne.getSelectionModel().getSelectedItem()));

        listCBoxArmy(cBoxArmyTwo);
        cBoxArmyTwo.getSelectionModel().selectedItemProperty().addListener((observableValue) -> setArmyTwo(cBoxArmyTwo.getSelectionModel().getSelectedItem()));

        listCboxTerrain();
    }

    /**
     * The method for listing the list of armies names in a comboBox
     * @param cBoxArmy the comboBox being filled with army names
     */
    private void listCBoxArmy(ComboBox<String> cBoxArmy) {
        cBoxArmy.getItems().clear();
        for (Army army : armies) {
            cBoxArmy.getItems().add(army.getName());
        }
    }

    /**
     * The method for setting armyOne for the battle
     * @param armyName the name of the army selected
     */
    private void setArmyOne(String armyName) {
        for (Army a : armies) {
            if (a.getName().equals(armyName))
                this.armyOne = a;
        }
        displayArmy(listArmyOneSetUp, txtCurrentArmyOne, armyOne);
    }

    /**
     * The method for setting armyTwo for the battle
     * @param armyName the name of the army selected
     */
    private void setArmyTwo(String armyName) {
        for (Army a : armies) {
            if (a.getName().equals(armyName))
                this.armyTwo = a;
        }
        displayArmy(listArmyTwoSetUp, txtCurrentArmyTwo, armyTwo);
    }

    /**
     * The method for displaying info about an army
     * @param armyList the listview where the info is displayed
     * @param armyName the label for displaying the name of the army
     * @param currentArmy the current selected army
     */
    private void displayArmy(ListView<String> armyList, Label armyName, Army currentArmy) {
        armyList.getItems().clear();
        if (currentArmy != null) {
            armyName.setText(currentArmy.getName());

            armyList.getItems().add("Total units: " + currentArmy.getAllUnits().size());
            armyList.getItems().add("");
            armyList.getItems().add("Infantry Units: " + currentArmy.getInfantryUnits().size());
            armyList.getItems().add("Ranged Units: " + currentArmy.getRangedUnits().size());
            armyList.getItems().add("Cavalry Units: " + currentArmy.getCavalryUnits().size());
            armyList.getItems().add("Commander Units: " + currentArmy.getCommanderUnits().size());
        }else
            armyName.setText("Current Army");
    }

    /**
     * The method for listing the different terrain in the comboBox
     * A listener to the comboBox is added here
     */
    private void listCboxTerrain() {
        cBoxTerrain.getItems().clear();
        String[] listTerrain = {String.valueOf(TERRAIN.HILL), String.valueOf(TERRAIN.FOREST), String.valueOf(TERRAIN.PLAINS)};
        for (int i = 0; i < TERRAIN.getNumberOfTerrains(); i++) {
            cBoxTerrain.getItems().add(listTerrain[i]);
        }
        cBoxTerrain.getSelectionModel().selectedItemProperty().addListener((observableValue) -> setTerrain(cBoxTerrain.getSelectionModel().getSelectedItem()));
    }

    /**
     * The method for setting the terrain of the battle
     * @param terrain the terrain being selected as a string
     */
    private void setTerrain(String terrain) {
        String[] listTerrain = {String.valueOf(TERRAIN.HILL), String.valueOf(TERRAIN.FOREST), String.valueOf(TERRAIN.PLAINS)};
        for (int i = 0; i < TERRAIN.getNumberOfTerrains(); i++) {
            if (terrain.equals(listTerrain[i])) {
                this.terrain = listTerrain[i];
                setTerrainImg(this.terrain);
            }
        }
    }

    /**
     * The method for displaying the current terrain image
     * @param terrain the terrain being displayed
     */
    private void setTerrainImg(String terrain) {
        var gc = terrainCanvas.getGraphicsContext2D();
        if (TERRAIN.getTerrain(terrain).equals(TERRAIN.PLAINS))
            gc.drawImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Plains.jpg"))), 0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        else if (TERRAIN.getTerrain(terrain).equals(TERRAIN.HILL))
            gc.drawImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/hills.jpg"))), 0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        else if (TERRAIN.getTerrain(terrain).equals(TERRAIN.FOREST))
            gc.drawImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/foggyForrest.jpg"))), 0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    /**
     * The method for switching to the battle simulation page
     * The instance of the Battle class is made and stored to a file before switching the page
     * @param event the event being handled
     */
    public void btnSwitchToBattleSimulationPage(ActionEvent event) {
        if (armyOne == null || armyTwo == null)
            AlertBox.alert("Error", "Please pick Armies to battle");
        else if (armyOne.equals(armyTwo))
            AlertBox.alert("Error", "Please pick different armies to battle");
        else if (!armyOne.hasUnits() || !armyTwo.hasUnits())
            AlertBox.alert("Error", "Pleas pick a army with units");
        else {
            if (terrain == null)
                terrain = String.valueOf(TERRAIN.getRandomTerrain());
            Battle battle = new Battle(armyOne, armyTwo, TERRAIN.getTerrain(terrain));

            try {
                FileManagement.writeBattleToFile(battle);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                AlertBox.alert("Error", "Something went wrong when trying to store battle data");
            }

            try {
                switchScene("/FXML/BattleSimulationPage.fxml", event);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                AlertBox.alert("Error", "Something went wrong while trying to load page.");
            }
        }
    }

    /**
     * The method for switching to the army editor page
     * @param event the event being handled
     */
    public void btnChangePageMakeArmy(ActionEvent event) {
        try {
            switchScene("/FXML/MakeArmyPage.fxml", event);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            AlertBox.alert("Error", "Something went wrong while trying to load page.");
        }
    }

    /**
     * The method for switching to the display armies page
     * @param event the event being handled
     */
    public void btnChangePageDisplayArmies(ActionEvent event) {
        try {
            switchScene("/FXML/DisplayArmies.fxml", event);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            AlertBox.alert("Error", "Something went wrong while trying to load page.");
        }
    }

    /**
     * The method for switching to the home page
     * @param event the event being handled
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
