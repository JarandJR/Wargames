package edu.ntnu.idatt2001.jarandjr.wargames.App.Controllers;

import edu.ntnu.idatt2001.jarandjr.wargames.FileManagement;
import edu.ntnu.idatt2001.jarandjr.wargames.App.AlertBox;
import edu.ntnu.idatt2001.jarandjr.wargames.Army;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Objects;
import javafx.scene.Node;
import java.util.List;
import java.net.URL;

/**
 * The controller for the home page
 */
public class StartUpPageController implements Initializable {

    List<Army> armies;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            armies = FileManagement.readListOfArmiesFromFile();
        }catch (IOException e) {
            System.out.println(e.getMessage());
            AlertBox.alert("Error", "Something went wrong while trying to load files. Try again");
        }
    }

    /**
     * The method for switching to the game startup page
     * @param event the event being handled
     */
    public void btnChangePageStartGame(ActionEvent event) {
        try {
            switchScene("/FXML/GameSetupPage.fxml", event);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            AlertBox.alert("Error", "Something went wrong while trying to load page.");
        }
    }

    /**
     * The method for switching to the make army page
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
     * The method for switching to sources page
     * @param event the event being handled
     */
    public void btnChangeToSourcesPage(ActionEvent event) {
        try {
            switchScene("/FXML/SourcesPage.fxml", event);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            AlertBox.alert("Error", "Something went wrong while trying to load page.");
        }
    }

    /**
     * The method for quitting the application
     */
    public void btnQuitGame() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You're about to exit the application");
        alert.setContentText("Do you want to save before exiting?: ");

        if (alert.showAndWait().get() == ButtonType.OK) {
            try{
                FileManagement.writeListOfArmiesToFile(armies);
            }catch (IOException e) {
                System.out.println(e.getMessage());
                AlertBox.alert("Error", "Something went wrong while trying to store files. Try again");
            }

            System.exit(0);
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
