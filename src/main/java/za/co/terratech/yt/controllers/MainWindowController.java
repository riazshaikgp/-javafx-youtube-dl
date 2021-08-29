/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.terratech.yt.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import za.co.terratech.yt.App;
import za.co.terratech.yt.youtubedl.YoutubeDLService;

/**
 * FXML Controller class
 *
 * @author riazs
 */
public class MainWindowController implements Initializable {

    YoutubeDLService service;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new YoutubeDLService();
        statusLabel.setText(service.update());
    }

    @FXML Group subPane;
    @FXML Label statusLabel;
    int index = 0;
    public void add(ActionEvent event) {
        try {
            Pane pane = FXMLLoader.load(getClass().getResource("/fxml/DownloadPane.fxml"));
            pane.setId("downloadpane"+index);
            subPane.getChildren().add(pane);
            pane.setTranslateY(index*185);
            index++;
            
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
