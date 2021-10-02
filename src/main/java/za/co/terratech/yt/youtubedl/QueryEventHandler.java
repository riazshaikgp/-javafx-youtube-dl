/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.terratech.yt.youtubedl;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author riazs
 */
public class QueryEventHandler implements EventHandler<ActionEvent> {

    boolean run = false;
    BackgroundThread thread;
    private final CheckBox chkVid;
    private final CheckBox chkAud;
    private final Label lblStatus;
    private final ChoiceBox<String> selVideo;
    private final ChoiceBox<String> selAudio;
    private final ProgressIndicator prgFetch;
    private final Button btnAuto;
    private Logger logger = LogManager.getLogger();

    public QueryEventHandler(BackgroundThread thread, CheckBox chkVid, CheckBox chkAud, Label lblStatus, ChoiceBox<String> selVideo, ChoiceBox<String> selAudio, ProgressIndicator prgFetch, Button btnAuto) {
        this.thread = thread;
        this.chkVid = chkVid;
        this.chkAud = chkAud;
        this.lblStatus = lblStatus;
        this.selVideo = selVideo;
        this.selAudio = selAudio;
        this.prgFetch= prgFetch;
        this.btnAuto =btnAuto;
    }

    
    
    @Override
    public void handle(ActionEvent event1) {
        if (thread.isWorkDone() && !run) {
            thread.setWorking(false);
            selVideo.getItems().addAll(thread.getService().getVideos());
                    selAudio.getItems().addAll(thread.getService().getAudios());
                    if (!thread.getService().getVideos().isEmpty()) {
                        chkVid.setDisable(false);
                    }
                    if (!thread.getService().getAudios().isEmpty()) {
                        chkAud.setDisable(false);
                    }
                    prgFetch.setProgress(100);
                    btnAuto.setDisable(false);
                    run=true;
        }
        
    }

    synchronized public boolean isRun() {
        return run;
    }
}
