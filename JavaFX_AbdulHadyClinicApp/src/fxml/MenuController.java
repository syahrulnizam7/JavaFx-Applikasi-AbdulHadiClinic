/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class MenuController implements Initializable {

    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private BorderPane rootLayout;
    
    @FXML
    Label user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        soundButton();
        changeScene("../fxml/Dashboard.fxml");
        // TODO
    }

    @FXML
    private void medicine(MouseEvent event) {
        soundButton();
        changeScene("../fxml/Medicine.fxml");
    }

    @FXML
    private void dashboard(MouseEvent event) {
        soundButton();
        changeScene("../fxml/Dashboard.fxml");
    }

    @FXML
    private void medicineCategory(MouseEvent event) {
        soundButton();
        changeScene("../fxml/MedicineCategory.fxml");
    }

    @FXML
    private void sales(MouseEvent event) {
        soundButton();
        changeScene("../fxml/Sales.fxml");
    }

    @FXML
    private void selectReport(MouseEvent event) {
        soundButton();
        changeScene("../fxml/SelectReport.fxml");
    }

    @FXML
    private void chart(MouseEvent event) {
        soundButton();
        changeScene("../fxml/Chart.fxml");
    }

    @FXML
    private void setting(MouseEvent event) {
        soundButton();
        changeScene("../fxml/Setting.fxml");
    }

    AudioClip tone = new AudioClip(getClass().getResource("/images/slebew.mp3").toString());
    @FXML
    private void logout(MouseEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../fxml/Login.fxml")));
            stage.setScene(scene);
            tone.play();
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void changeScene(String scenePath) {

        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource(scenePath));
        AnchorPane pane = new AnchorPane();
        try {
            pane = (AnchorPane) loader.load();
            rootLayout.setCenter(pane);
        } catch (Exception e) {
        }

    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    final AudioClip tone1 = new AudioClip(getClass().getResource("/images/sfxbutton.mp3").toString());
    final AudioClip tone2 = new AudioClip(getClass().getResource("/images/sfxwrong.mp3").toString());

    public void soundButton() {
        tone1.play();
    }

    public void soundButton2() {
        tone2.play();
    }

}
