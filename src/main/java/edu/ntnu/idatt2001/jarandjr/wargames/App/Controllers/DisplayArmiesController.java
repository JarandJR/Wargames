package edu.ntnu.idatt2001.jarandjr.wargames.App.Controllers;

import edu.ntnu.idatt2001.jarandjr.wargames.ENUMS.TERRAIN;
import edu.ntnu.idatt2001.jarandjr.wargames.FileManagement;
import edu.ntnu.idatt2001.jarandjr.wargames.App.AlertBox;
import edu.ntnu.idatt2001.jarandjr.wargames.Units.*;
import edu.ntnu.idatt2001.jarandjr.wargames.Army;
import javafx.scene.control.ListView;
import javafx.scene.control.ComboBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
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
 * The controller for the Display armies page
 */
public class DisplayArmiesController implements Initializable {

    @FXML
    public Label txtCurrentArmy;
    public Canvas imgDisplayArmiesUnit;
    public ComboBox<String> cBoxListOfArmies;
    public ListView<String> listCurrentArmyInfo;
    public ListView<String> listCurrentUnitInfo;

    Army currentArmy;
    List<Army> armies;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imgDisplayArmiesUnit.getGraphicsContext2D().clearRect( 0, 0, imgDisplayArmiesUnit.getWidth(), imgDisplayArmiesUnit.getHeight());
        try{
            armies = FileManagement.readListOfArmiesFromFile();
        }catch (IOException e) {
            System.out.println(e.getMessage());
            AlertBox.alert("Error", "Something went wrong while trying to load files. Try again");
        }
        listCBoxArmies();
    }

    /**
     * The method for listing the armies in a comboBox
     * This also adds a listener to the comboBox
     */
    private void listCBoxArmies() {
        cBoxListOfArmies.getItems().clear();
        for (Army army : armies) {
            cBoxListOfArmies.getItems().add(army.getName());
        }
        cBoxListOfArmies.getSelectionModel().selectedItemProperty().addListener((observableValue) -> setCurrentArmy(cBoxListOfArmies.getSelectionModel().getSelectedItem()));
    }

    /**
     * The method for setting the current army or the current army selected
     * @param armyName the name of the selected army
     */
    private void setCurrentArmy(String armyName) {
        imgDisplayArmiesUnit.getGraphicsContext2D().clearRect( 0, 0, imgDisplayArmiesUnit.getWidth(), imgDisplayArmiesUnit.getHeight());
        for (Army a : armies) {
            if (a.getName().equals(armyName)) {
                this.currentArmy = a;
            }
        }
        displayCurrentArmy();
    }

    /**
     * The method for displaying info about the current army
     */
    private void displayCurrentArmy() {
        listCurrentArmyInfo.getItems().clear();
        if (currentArmy != null) {
            txtCurrentArmy.setText(currentArmy.getName());

            listCurrentArmyInfo.getItems().add("Total units: " + currentArmy.getAllUnits().size());
            listCurrentArmyInfo.getItems().add("");
            listCurrentArmyInfo.getItems().add("Infantry Units: " + currentArmy.getInfantryUnits().size());
            listCurrentArmyInfo.getItems().add("Ranged Units: " + currentArmy.getRangedUnits().size());
            listCurrentArmyInfo.getItems().add("Cavalry Units: " + currentArmy.getCavalryUnits().size());
            listCurrentArmyInfo.getItems().add("Commander Units: " + currentArmy.getCommanderUnits().size());

            listCurrentArmyInfo.getSelectionModel().selectedItemProperty().addListener((Observable) -> {
                String[] info = listCurrentArmyInfo.getSelectionModel().getSelectedItem().split(":");
                if (!info[0].trim().equalsIgnoreCase("Total units") && !info[0].isBlank()) {
                    String[] unitTypeSplit = info[0].split(" ");
                    String unitType = unitTypeSplit[0] + unitTypeSplit[1];
                    StringBuilder sb = new StringBuilder(unitType);
                    displayCurrentUnitInfo(String.valueOf(sb.deleteCharAt(sb.length()-1)));
                }
            });
        }else
            txtCurrentArmy.setText("Current Army");
    }

    /**
     * The method for displaying the current unit type
     * @param currentUnitType the current unit type selected
     */
    private void displayCurrentUnitInfo(String currentUnitType) {
        listCurrentUnitInfo.getItems().clear();
        imgDisplayArmiesUnit.getGraphicsContext2D().clearRect( 0, 0, imgDisplayArmiesUnit.getWidth(), imgDisplayArmiesUnit.getHeight());
        switch (currentUnitType) {
            case "InfantryUnit" -> {
                displayUnit(new InfantryUnit("Infantry Unit", 100));
                imgDisplayArmiesUnit.getGraphicsContext2D().drawImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/InfantryUnit.png"))), 0, 0, imgDisplayArmiesUnit.getWidth(), imgDisplayArmiesUnit.getHeight());
            }
            case "RangedUnit" -> {
                displayUnit(new RangedUnit("Ranged Unit", 100));
                imgDisplayArmiesUnit.getGraphicsContext2D().drawImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/RangedUnit.png"))), 0, 0, imgDisplayArmiesUnit.getWidth(), imgDisplayArmiesUnit.getHeight());
            }
            case "CavalryUnit" -> {
                displayUnit(new CavalryUnit("Cavalry Unit", 100));
                imgDisplayArmiesUnit.getGraphicsContext2D().drawImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/CavalryUnit.png"))), 0, 0, imgDisplayArmiesUnit.getWidth(), imgDisplayArmiesUnit.getHeight());
            }
            case "CommanderUnit" -> {
                displayUnit(new CommanderUnit("Commander Unit", 180));
                imgDisplayArmiesUnit.getGraphicsContext2D().drawImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/CommanderUnit.png"))), 0, 0, imgDisplayArmiesUnit.getWidth(), imgDisplayArmiesUnit.getHeight());
            }
        }
    }

    /**
     * The method for displaying info about the current unit type
     * @param unit the unit type being displayed
     */
    private void displayUnit(Unit unit) {
        listCurrentUnitInfo.getItems().add(unit.getName());
        listCurrentUnitInfo.getItems().add("");
        listCurrentUnitInfo.getItems().add("Health: " + unit.getHealth());
        listCurrentUnitInfo.getItems().add("Attack: " + unit.getAttack());
        listCurrentUnitInfo.getItems().add("Armor: " + unit.getArmor());
        listCurrentUnitInfo.getItems().add("");
        listCurrentUnitInfo.getItems().add("Attack bonuses / Resist bonuses");
        listCurrentUnitInfo.getItems().add(TERRAIN.FOREST + ": " + unit.getAttackBonus(TERRAIN.FOREST) + " / " + unit.getResistBonus(TERRAIN.FOREST));
        listCurrentUnitInfo.getItems().add(TERRAIN.PLAINS + ": " + unit.getAttackBonus(TERRAIN.PLAINS) + " / " + unit.getResistBonus(TERRAIN.PLAINS));
        listCurrentUnitInfo.getItems().add(TERRAIN.HILL + ": " + unit.getAttackBonus(TERRAIN.HILL) + " / " + unit.getResistBonus(TERRAIN.HILL));
    }

    /**
     * The method for switching to the Game startup page
     * @param event the event being handled
     */
    public void btnSwitchPageGameStartUp(ActionEvent event) {
        try {
            switchScene("/FXML/GameSetupPage.fxml", event);
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
