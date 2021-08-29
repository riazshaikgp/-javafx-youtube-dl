/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.terratech.yt.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;
import za.co.terratech.yt.App;
import za.co.terratech.yt.youtubedl.DLThread;
import za.co.terratech.yt.youtubedl.YoutubeDLService;

/**
 * FXML Controller class
 *
 * @author riazs
 */
public class DownloadPaneController implements Initializable {

    YoutubeDLService service;
    String directory = "";

    @FXML
    Group subPane;
    @FXML
    private CheckBox chkVid;
    @FXML
    private CheckBox chkAud;
    @FXML
    private Label lblStatus;
    @FXML
    private Button btnDirectory;
    @FXML
    private Button btnDownload;
    @FXML
    private ChoiceBox<String> selVideo;
    @FXML
    private Button urlQueryBtn;
    @FXML
    private Label lblDirectory;
    @FXML
    private TextField txtUrl;
    @FXML
    private ProgressBar prgDownload;

    @FXML
    private ChoiceBox<String> selAudio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new YoutubeDLService();
    }

    @FXML
    public void query(ActionEvent event) {
        Button queryButton = (Button) event.getSource();
        Pane parent = (Pane) queryButton.getParent();
        service.checkFormats(txtUrl.getText());
        selVideo.getItems().addAll(service.getVideos());
        selAudio.getItems().addAll(service.getAudios());
        if (!service.getVideos().isEmpty()) {
            chkVid.setDisable(false);
        }
        if (!service.getAudios().isEmpty()) {
            chkAud.setDisable(false);
        }
        lblStatus.setText("Info Fetched, select your formats");
    }

    @FXML
    void directory(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File selectedDirectory = directoryChooser.showDialog(App.getStage());
        if (selectedDirectory != null) {
            directory = selectedDirectory.getAbsolutePath();
            lblDirectory.setText(directory);
        }
    }

    @FXML
    public void download(ActionEvent event) {
        DLThread downloader = service.download(chkVid.isSelected(), chkAud.isSelected(), selVideo.getValue(), (String) selAudio.getValue(), txtUrl.getText(), directory + "\\%(title)s-%(id)s.%(ext)s");
        Timeline timer = new Timeline(
                new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        lblStatus.setText(downloader.extractor().getSize() + " at " + downloader.extractor().getSpeed());
                        prgDownload.setProgress(downloader.extractor().getProgress() / 100);
                        if (!downloader.extractor().status()) {
                            lblStatus.setText("Done :)");
                        }
                    }
                }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    @FXML
    void enableVideo(ActionEvent event) {
        selVideo.setDisable(!chkVid.isSelected());
    }

    @FXML
    void enableAudio(ActionEvent event) {
        selAudio.setDisable(!chkAud.isSelected());
    }

}
