package edu.ntnu.idatt2001.jarandjr.wargames.App.Controllers;

import edu.ntnu.idatt2001.jarandjr.wargames.App.AlertBox;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
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
import java.net.URL;

/**
 * The controller for the source page
 */
public class SourcesController implements Initializable {

    @FXML
    public Label txtAreaSources;

    /**
     * The initializing happening when the page gets entered
     * This method displays the sources on the text area
     *
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtAreaSources.setText(getSources());
    }

    /**
     * The method for switching to the front page of the application
     * @param event the button pressed
     */
    public void btnSwitchToStartUpPageFromSources(ActionEvent event) {
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

    /**
     * The method for getting the sources as a string
     * @return The sources as a string
     */
    private String getSources() {
        final String NEWLINE = "\n";
        StringBuilder sb = new StringBuilder();

        sb.append("Image of foggy winter forrest").append(NEWLINE);
        sb.append("Link: https://www.google.com/imgres?imgurl=https%3A%2F%2Fmedia.istockphoto.com%2Fphotos%2Fmisty-mountains-picture-id1067955260%3Fb%3D1%26k%3D20%26m%3D1067955260%26s%3D170667a%26w%3D0%26h%3Db_cARZwQArhMYUEVHJJqeVpJzJyaq80qAbjH7CfljxQ%3D&imgrefurl=https%3A%2F%2Funsplash.com%2Fs%2Fphotos%2Fsnow-forest&tbnid=UPfdNkgTlWhRuM&vet=12ahUKEwi--rSM25H3AhWPr6QKHfqIB2QQMygCegUIARDDAQ..i&docid=84zTbcqzfEOXEM&w=509&h=339&q=foggy%20winter%20forest%20wallpaper&hl=no&ved=2ahUKEwi--rSM25H3AhWPr6QKHfqIB2QQMygCegUIARDDAQ").append(NEWLINE);
        sb.append(NEWLINE);
        sb.append("Image of foggyForrest").append(NEWLINE).append("Link: https://images.photowall.com/products/58341/foggy-forest-4.jpg?h=699&q=85").append(NEWLINE);
        sb.append(NEWLINE);
        sb.append("Image of hills").append(NEWLINE).append("Link: https://c4.wallpaperflare.com/wallpaper/1020/737/303/nature-landscape-trees-mist-wallpaper-preview.jpg").append(NEWLINE);
        sb.append(NEWLINE);
        sb.append("Image of plains").append(NEWLINE).append("Link: https://upload.wikimedia.org/wikipedia/commons/9/95/Horton_Plains_2021.jpg").append(NEWLINE);
        sb.append(NEWLINE);
        sb.append("Image of InfantryUnit").append(NEWLINE).append("Link: https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pngwing.com%2Fen%2Ffree-png-imrxv&psig=AOvVaw2yQUZWGZmVEfbfpHf51zZU&ust=1651764185257000&source=images&cd=vfe&ved=0CAwQjRxqFwoTCMiHqaCTxvcCFQAAAAAdAAAAABAT").append(NEWLINE);
        sb.append(NEWLINE);
        sb.append("Image of RangedUnit").append(NEWLINE).append("Link: https://www.pngaaa.com/detail/5105685").append(NEWLINE);
        sb.append(NEWLINE);
        sb.append("Image of CavalryUnit").append(NEWLINE).append("Link: https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.subpng.com%2Fpng-r73rnd%2F&psig=AOvVaw2yQUZWGZmVEfbfpHf51zZU&ust=1651764185257000&source=images&cd=vfe&ved=0CAwQjRxqFwoTCMiHqaCTxvcCFQAAAAAdAAAAABAO").append(NEWLINE);
        sb.append(NEWLINE);
        sb.append("Image of CommanderUnit").append(NEWLINE).append("Link: https://www.pngwing.com/en/free-png-nreql").append(NEWLINE);

        return String.valueOf(sb);
    }
}
