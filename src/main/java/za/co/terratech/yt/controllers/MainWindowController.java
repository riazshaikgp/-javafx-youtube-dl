/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.terratech.yt.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import za.co.terratech.yt.model.PlaylistEntry;
import za.co.terratech.yt.youtubedl.PlaylistThread;
import za.co.terratech.yt.youtubedl.YoutubeDLService;

/**
 * FXML Controller class
 *
 * @author riazs
 */
public class MainWindowController implements Initializable {

    YoutubeDLService service;

    @FXML
    private Button btnPlaylist;
    @FXML
    private Button addDownloaderBtn;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Group subPane;
    @FXML
    private Pane mainPane;
    @FXML
    private Label statusLabel;
    @FXML
    private TextField txtPlaylist;

    private Logger logger = LogManager.getLogger();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new YoutubeDLService();
        statusLabel.setText(service.update());
        String classpath = System.getProperty("java.class.path");
        String[] classpathEntries = classpath.split(File.pathSeparator);
        for (String path : classpathEntries) {
            logger.info(path);
        }
    }

    int index = 0;

    @FXML
    public void add(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DownloadPane.fxml"));
            DownloadPaneController controller = new DownloadPaneController();
            loader.setController(controller);
            Pane pane = loader.load();
            pane.setId("downloadpane" + index);
            subPane.getChildren().add(pane);
            pane.setTranslateY(index * 185);
            index++;

        } catch (IOException ex) {
            logger.info(ex, ex);
        }
    }

    @FXML
    void loadPlaylist(ActionEvent event) {
        PlaylistThread thread = new PlaylistThread(txtPlaylist.getText());
        thread.start();

        Timeline timer = new Timeline(
                new KeyFrame(Duration.seconds(1), (ActionEvent event1) -> {
                    if (thread.isWorkDone()) {
                        thread.setWorking(false);
                        for (PlaylistEntry entry : thread.getEntries()) {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DownloadPane.fxml"));
                                DownloadPaneController controller = new DownloadPaneController(entry, event);
                                loader.setController(controller);
                                Pane pane = loader.load();
                                pane.setId("downloadpane" + index);
                                subPane.getChildren().add(pane);
                                pane.setTranslateY(index * 185);
                                index++;

                            } catch (IOException ex) {
                                logger.info(ex, ex);
                            }
                        }
                    }
                }));
        timer.play();
    }
}
