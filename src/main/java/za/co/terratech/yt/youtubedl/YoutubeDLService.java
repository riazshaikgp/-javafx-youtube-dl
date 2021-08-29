/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.terratech.yt.youtubedl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;

/**
 *
 * @author riazs
 */
public class YoutubeDLService {

    private List<String> videos;
    private List<String> audios;
    DLThread downloader;

    public String update() {
        try {
            Runtime rt = Runtime.getRuntime();
            String[] commands = {"youtube-dl.exe", "-U"};
            Process proc = rt.exec(commands);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            // Read the output from the command
            String message = "";
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                message = message + s + "\n";
            }
            // Read any errors from the attempted command
            while ((s = stdError.readLine()) != null) {
                message = message + s;
            }
            System.out.println(message);
            return message;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void checkFormats(String url) {
        try {
            videos = new ArrayList();
            audios = new ArrayList();
            Runtime rt = Runtime.getRuntime();
            String[] commands = {"youtube-dl.exe", "-F", url};
            Process proc = rt.exec(commands);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            // Read the output from the command
            String message = "";
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                if (s.contains("video only")) {
                    videos.add(s);
                    System.out.println(s);
                }
                if (s.contains("audio only")) {
                    audios.add(s);
                    System.out.println(s);
                }
            }
            // Read any errors from the attempted command
            while ((s = stdError.readLine()) != null) {
                message = message + s;
            }
            System.out.println(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<String> getVideos() {
        return videos;
    }

    public List<String> getAudios() {
        return audios;
    }

    synchronized public DLThread download(boolean wantvideo, boolean wantaudio, String video, String audio, String input, String path) {
        downloader = new DLThread(wantvideo, wantaudio, video, audio, input, path);
        Platform.runLater(downloader);
        return downloader;
    }

}
