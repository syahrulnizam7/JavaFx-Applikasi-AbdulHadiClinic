package home;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    AudioClip tone = new AudioClip(getClass().getResource("/images/selamatdatang.mpeg").toString());
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setMaximized(false);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        tone.play();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}