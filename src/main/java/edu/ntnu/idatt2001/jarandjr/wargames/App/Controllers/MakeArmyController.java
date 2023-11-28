package edu.ntnu.idatt2001.jarandjr.wargames.App.Controllers;

import edu.ntnu.idatt2001.jarandjr.wargames.ENUMS.TERRAIN;
import edu.ntnu.idatt2001.jarandjr.wargames.ENUMS.UNIT_TYPE;
import edu.ntnu.idatt2001.jarandjr.wargames.FileManagement;
import edu.ntnu.idatt2001.jarandjr.wargames.App.AlertBox;
import edu.ntnu.idatt2001.jarandjr.wargames.UnitFactory;
import edu.ntnu.idatt2001.jarandjr.wargames.Units.*;
import edu.ntnu.idatt2001.jarandjr.wargames.Army;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.fxml.FXML;

import java.util.ResourceBundle;
import java.io.IOException;
import java.util.Objects;
import java.util.List;
import java.net.URL;

/**
 * The controller for the make army page
 */
public class MakeArmyController implements Initializable {

    @FXML
    public Canvas imgUnitType;
    public Label txtCurrentArmy;
    public Label txtNumberOfUnits;
    public Label txtCurrentUnitType;
    public TextField txtInpArmyName;
    public Slider sliderNumberOfUnits;
    public ListView<String> listArmyInfo;
    public ListView<String> listUnitInfo;
    public ComboBox<String> cBoxUnitType;
    public ComboBox<String> cBoxListArmies;

    private final String[] listOfTypes = {"InfantryUnit", "RangedUnit", "CavalryUnit", "CommanderUnit"};

    Army currentArmy;
    List<Army> armies;
    String currentUnitType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imgUnitType.getGraphicsContext2D().clearRect(0, 0, imgUnitType.getWidth(), imgUnitType.getHeight());
        try{
            armies = FileManagement.readListOfArmiesFromFile();
        }catch (IOException e) {
            System.out.println(e.getMessage());
            AlertBox.alert("Error", "Something went wrong while trying to load files. Try again");
        }
        setSlider();
        listCBoxUnitType();
        listCBoxArmies();
    }

    /**
     * The method for adding event handlers to the slider for number of units
     */
    private void setSlider() {
        txtNumberOfUnits.setText("Number of Units: " + (int)sliderNumberOfUnits.getValue());
        sliderNumberOfUnits.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::changeLabelHandler);
        sliderNumberOfUnits.addEventHandler(MouseEvent.MOUSE_CLICKED, this::changeLabelHandler);
    }

    /**
     * The method for changing the label for number of units selected
     * @param e the event being handled
     */
    private void changeLabelHandler(MouseEvent e) {
        txtNumberOfUnits.setText("Number of Units: " + (int)sliderNumberOfUnits.getValue());
    }

    /**
     * The method for listing the different unit-types in the combobox for selecting the current unit type
     */
    private void listCBoxUnitType() {
        for (int i = 0; i < UNIT_TYPE.getNumberOfTypes(); i++) {
            cBoxUnitType.getItems().add(listOfTypes[i]);
        }
        cBoxUnitType.getSelectionModel().selectedItemProperty().addListener((observableValue) -> {
            setCurrentUnitType(cBoxUnitType.getSelectionModel().getSelectedItem());
            setUnitImage();
        });
    }

    /**
     * The method for listing the armies stored in files in the comboBox for selecting the current army
     */
    private void listCBoxArmies() {
        cBoxListArmies.getItems().clear();
        for (Army army : armies) {
            cBoxListArmies.getItems().add(army.getName());
        }
        cBoxListArmies.getSelectionModel().selectedItemProperty().addListener((observableValue) -> setCurrentArmy(cBoxListArmies.getSelectionModel().getSelectedItem()));
    }

    /**
     * The method for setting the current unit type
     * @param currentUnitType the current unit type being sat
     */
    private void setCurrentUnitType(String currentUnitType) {
        this.currentUnitType = currentUnitType;
        displayCurrentUnitInfo();
    }

    /**
     * The method for displaying the current unit type
     */
    private void displayCurrentUnitInfo() {
        listUnitInfo.getItems().clear();
        txtCurrentUnitType.setText(currentUnitType);

        switch (currentUnitType) {
            case "InfantryUnit" -> displayUnit(new InfantryUnit("Infantry Unit", 100));
            case "RangedUnit" -> displayUnit(new RangedUnit("Ranged Unit", 100));
            case "CavalryUnit" -> displayUnit(new CavalryUnit("Cavalry Unit", 100));
            case "CommanderUnit" -> displayUnit(new CommanderUnit("Commander Unit", 180));
        }
    }

    /**
     * The method for displaying info about a unit type
     * @param unit the unit type being displayed
     */
    private void displayUnit(Unit unit) {
        listUnitInfo.getItems().add("Health: " + unit.getHealth());
        listUnitInfo.getItems().add("Attack: " + unit.getAttack());
        listUnitInfo.getItems().add("Armor: " + unit.getArmor());
        listUnitInfo.getItems().add("");
        listUnitInfo.getItems().add("Attack bonuses / Resist bonuses");
        listUnitInfo.getItems().add(TERRAIN.FOREST + ": " + unit.getAttackBonus(TERRAIN.FOREST) + " / " + unit.getResistBonus(TERRAIN.FOREST));
        listUnitInfo.getItems().add(TERRAIN.PLAINS + ": " + unit.getAttackBonus(TERRAIN.PLAINS) + " / " + unit.getResistBonus(TERRAIN.PLAINS));
        listUnitInfo.getItems().add(TERRAIN.HILL + ": " + unit.getAttackBonus(TERRAIN.HILL) + " / " + unit.getResistBonus(TERRAIN.HILL));
    }

    /**
     * The method for setting the current army
     * @param armyName the name of the army being sat as the current army
     */
    private void setCurrentArmy(String armyName) {
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
        listArmyInfo.getItems().clear();
        if (currentArmy != null) {
            txtCurrentArmy.setText(currentArmy.getName());

            listArmyInfo.getItems().add("Total units: " + currentArmy.getAllUnits().size());
            listArmyInfo.getItems().add("");
            listArmyInfo.getItems().add("Infantry Units: " + currentArmy.getInfantryUnits().size());
            listArmyInfo.getItems().add("Ranged Units: " + currentArmy.getRangedUnits().size());
            listArmyInfo.getItems().add("Cavalry Units: " + currentArmy.getCavalryUnits().size());
            listArmyInfo.getItems().add("Commander Units: " + currentArmy.getCommanderUnits().size());
        }else
            txtCurrentArmy.setText("Current Army");
    }

    /**
     * The method for displaying an image of the current unit type
     */
    private void setUnitImage() {
        imgUnitType.getGraphicsContext2D().clearRect(0, 0, imgUnitType.getWidth(), imgUnitType.getHeight());
        if (UNIT_TYPE.getType(currentUnitType).equals(UNIT_TYPE.INFANTRY_UNIT))
            imgUnitType.getGraphicsContext2D().drawImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/InfantryUnit.png"))), 0, 0, imgUnitType.getWidth(), imgUnitType.getHeight());
        else if (UNIT_TYPE.getType(currentUnitType).equals(UNIT_TYPE.RANGED_UNIT))
            imgUnitType.getGraphicsContext2D().drawImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/RangedUnit.png"))), 0, 0, imgUnitType.getWidth(), imgUnitType.getHeight());
        else if (UNIT_TYPE.getType(currentUnitType).equals(UNIT_TYPE.CAVALRY_UNIT))
            imgUnitType.getGraphicsContext2D().drawImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/CavalryUnit.png"))), 0, 0, imgUnitType.getWidth(), imgUnitType.getHeight());
        else if (UNIT_TYPE.getType(currentUnitType).equals(UNIT_TYPE.COMMANDER_UNIT))
            imgUnitType.getGraphicsContext2D().drawImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/CommanderUnit.png"))), 0, 0, imgUnitType.getWidth(), imgUnitType.getHeight());
    }

    /**
     * The method for updating the displays
     */
    private void updateDisplays() {
        storeArmiesInFiles();
        listCBoxArmies();
        displayCurrentArmy();
    }

    /**
     * The method for storing the armies to a file
     */
    private void storeArmiesInFiles() {
        try{
            FileManagement.writeListOfArmiesToFile(armies);
        }catch (IOException e) {
            System.out.println(e.getMessage());
            AlertBox.alert("Error", "Something went wrong while trying to load files. Try again");
        }
    }

    /**
     * The method being called by the make army button
     * The method for making an army with a army name
     * Current army is selected to be the new army
     */
    public void btnMakeArmy() {
        if (txtInpArmyName.getText().isBlank()) {
            AlertBox.alert("Error", "Please provide a Army Name!");
            return;
        }
        for (Army a : armies) {
            if (a.getName().equals(txtInpArmyName.getText())){
                AlertBox.alert("Error", "The Army with the name: '" + txtInpArmyName.getText() + "' already exist");
                return;
            }
        }
        Army newArmy = new Army(txtInpArmyName.getText());
        this.currentArmy = newArmy;
        armies.add(newArmy);
        updateDisplays();
    }

    /**
     * The method for deleting the current army
     * Called by the delete army button
     */
    public void btnDeleteCurrentArmy() {
        if (currentArmy != null) {
            for (Army a : armies) {
                if (a.equals(currentArmy)) {
                    armies.remove(currentArmy);
                    currentArmy = null;
                    updateDisplays();
                    break;
                }
            }
        }
        else
            AlertBox.alert("Error", "Please pick a army to delete first");
    }

    /**
     * The method for adding units to the current army
     * Called by the add units button
     */
    public void btnAddUnitsToArmy() {

        int numberOfUnits;
        try {
            numberOfUnits = (int)sliderNumberOfUnits.getValue();
        }catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            AlertBox.alert("Error", "Invalid input on Number of Units with the input: '" + txtNumberOfUnits.getText() + "'");
            return;
        }

        int MAX_NUMBER_OF_UNITS = 1680;
        if (currentUnitType == null || currentUnitType.isBlank())
            AlertBox.alert("Error", "Please pick a Unit Type");
        else if (currentArmy == null)
            AlertBox.alert("Error", "Please pick or make a Army first");
        else if (currentArmy.getSize() + numberOfUnits > MAX_NUMBER_OF_UNITS)
            AlertBox.alert("Error", "Max number of units in an army is: " + MAX_NUMBER_OF_UNITS);
        else {
            int unitHealth = (UNIT_TYPE.getType(currentUnitType).equals(UNIT_TYPE.COMMANDER_UNIT)) ? 180 : 100;
            currentArmy.addAll(UnitFactory.makeListOfUnits(numberOfUnits, UNIT_TYPE.getType(currentUnitType), currentUnitType, unitHealth));
            updateDisplays();
        }
    }

    /**
     * The method for removing units from the current army
     * Called by the remove units button
     */
    public void btnRemoveUnits() {
        int numberOfUnits;
        try {
            numberOfUnits = (int)sliderNumberOfUnits.getValue();
        }catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            AlertBox.alert("Error", "Invalid input on Number of Units with the input: '" + txtNumberOfUnits.getText() + "'");
            return;
        }

        if (currentUnitType == null || currentUnitType.isBlank())
            AlertBox.alert("Error", "Please pick a Unit Type");
        else if (currentArmy == null)
            AlertBox.alert("Error", "Please pick or make a Army first");

        else {
            List<Unit> unitTypeBeingRemoved = null;
            if (UNIT_TYPE.getType(currentUnitType).equals(UNIT_TYPE.INFANTRY_UNIT))
                unitTypeBeingRemoved = currentArmy.getInfantryUnits();
            else if (UNIT_TYPE.getType(currentUnitType).equals(UNIT_TYPE.RANGED_UNIT))
                unitTypeBeingRemoved = currentArmy.getRangedUnits();
            else if (UNIT_TYPE.getType(currentUnitType).equals(UNIT_TYPE.CAVALRY_UNIT))
                unitTypeBeingRemoved = currentArmy.getCavalryUnits();
            else if (UNIT_TYPE.getType(currentUnitType).equals(UNIT_TYPE.COMMANDER_UNIT))
                unitTypeBeingRemoved = currentArmy.getCommanderUnits();

            if (unitTypeBeingRemoved != null && unitTypeBeingRemoved.size() < numberOfUnits)
                AlertBox.alert("Error", "Number of units is greater than units in the army");
            else {
                for (int i = 0; i < numberOfUnits; i++) {
                    if (unitTypeBeingRemoved != null) {
                        currentArmy.remove(unitTypeBeingRemoved.get(i));
                    }
                }
            }
            updateDisplays();
        }
    }

    /**
     * The method for switching to the game startup page
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
     * */
    private void switchScene(String path, ActionEvent event)throws IOException{
        storeArmiesInFiles();
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
