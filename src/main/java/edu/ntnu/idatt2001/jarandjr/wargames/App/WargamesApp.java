package edu.ntnu.idatt2001.jarandjr.wargames.App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * The main method launching the application
 */
public class WargamesApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The method for starting up the application
     *
     * @param stage the front page of the application
     * @throws Exception if something goes wrong when loading the page
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/StartUpPage.fxml")));
        stage.setTitle("Wargames");
        Scene scene = new Scene(root);
        String css = Objects.requireNonNull(this.getClass().getResource("/Styles/WargamesStyleSheet.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
