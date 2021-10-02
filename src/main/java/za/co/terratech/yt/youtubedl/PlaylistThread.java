/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.terratech.yt.youtubedl;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;
import za.co.terratech.yt.model.PlaylistEntry;

/**
 *
 * @author riazs
 */
public class PlaylistThread extends Thread {

    private final String playlist;
    List<PlaylistEntry> entries;
    boolean status = false;
    private org.apache.logging.log4j.Logger logger = LogManager.getLogger();

    public PlaylistThread(String playlist) {
        this.playlist = playlist;
        entries = new ArrayList();
    }

    @Override
    public void run() {
        String[] commands = {"youtube-dl.exe", "-j", "--flat-playlist", playlist};
        execute(commands);

    }

    synchronized private void execute(String[] commands) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(commands);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String s = null;
            String message = "";

            while ((s = stdInput.readLine()) != null) {
                Gson gson = new Gson();
                entries.add(gson.fromJson(s, PlaylistEntry.class));
            }
            while ((s = stdError.readLine()) != null) {
                message = message + s;
            }
            status = true;
        } catch (IOException ex) {
            logger.error(ex, ex);
        }

    }

    synchronized public List<PlaylistEntry> getEntries() {
        return entries;
    }

    synchronized public boolean isWorkDone() {
        return status;
    }

    synchronized public void setWorking(boolean status) {
        this.status = status;
    }

}
