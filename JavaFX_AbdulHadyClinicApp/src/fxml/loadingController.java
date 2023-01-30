package fxml;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.animation.RotateTransition;
import javafx.animation.RotateTransitionBuilder;
import javafx.animation.SequentialTransition;
import javafx.animation.SequentialTransitionBuilder;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class loadingController implements Initializable {

    @FXML
    private ProgressBar PB;

    @FXML
    private ProgressIndicator PI;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label text;

    @FXML
    private ImageView imgView;
    private SequentialTransition sequentialTransition;

    public void play() {
        sequentialTransition.play();
    }

    AudioClip tone = new AudioClip(getClass().getResource("/images/welcome.mp3").toString());

    File file = new File("./src/fxml");
    File[] listOfFile = file.listFiles();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RotateTransition rotateTransition
                = RotateTransitionBuilder.create()
                        .duration(Duration.seconds(2))
                        .byAngle(180)
                        .cycleCount(4)
                        .autoReverse(true)
                        .build();
        FadeTransition fadeTransition
                = FadeTransitionBuilder.create()
                        .duration(Duration.seconds(1))
                        .fromValue(1)
                        .toValue(0.3)
                        .cycleCount(2)
                        .autoReverse(true)
                        .build();
        sequentialTransition
                = SequentialTransitionBuilder.create()
                        .node(imgView)
                        .children(rotateTransition, fadeTransition)
                        .cycleCount(Timeline.INDEFINITE)
                        .autoReverse(true)
                        .build();

        play();
        tone.play();

        Task task = taskWorker(listOfFile.length);
        PB.progressProperty().unbind();
        PI.progressProperty().unbind();
        PB.progressProperty().bind(task.progressProperty());
        PI.progressProperty().bind(task.progressProperty());
        task.messageProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                text.setText(newValue);
            }
        });
        task.setOnSucceeded(e -> {
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.close();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/Menu.fxml"));
                Stage stage2 = new Stage();
                stage2.initStyle(StageStyle.TRANSPARENT);
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                stage2.setScene(scene);
                stage2.show();
                tone.stop();
            } catch (IOException ex) {
                Logger.getLogger(loadingController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Thread thread = new Thread(task);
        thread.start();
    }

    private Task taskWorker(int seconds) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < seconds; i++) {
                    updateProgress(i + 1, seconds);
                    updateProgress(i, listOfFile.length);
                    updateMessage(listOfFile[i].getName());
                    Thread.sleep(217);
                }
                return true;
            }
        };
    }
}
