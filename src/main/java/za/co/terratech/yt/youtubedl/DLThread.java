/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.terratech.yt.youtubedl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riazs
 */
public class DLThread implements Runnable {

    private String video;
    private String audio;
    private final String input;
    private final String path;
    private final boolean wantvideo;
    private final boolean wantaudio;
    private String message;
    private StreamProcessExtractor extractor;

    public DLThread(boolean wantvideo, boolean wantaudio, String video, String audio, String input, String path) {
        this.video = video;
        this.audio = audio;
        this.input = input;
        this.path = path;
        this.wantvideo = wantvideo;
        this.wantaudio = wantaudio;
    }

    @Override
    public void run() {

        if (video != null && !video.isEmpty()) {
            video = video.substring(0, video.indexOf(" "));
        }
        if (audio != null && !audio.isEmpty()) {
            audio = audio.substring(0, audio.indexOf(" "));
        }

        String formats;

        if (wantvideo && wantaudio) {
            formats = video + "+" + audio;
            String[] commands = {"youtube-dl.exe", "-f", formats, input, "--merge-output-format", "mp4", "--output", path};
            execute(commands);
            
        }
        if (wantvideo && !wantaudio) {
            formats = video;
            String[] commands = {"youtube-dl.exe", "-f", formats, input, "--merge-output-format", "mp4", "--output", path};
            execute(commands);
        }
        if (wantaudio && !wantvideo) {
            formats = audio;
            String[] commands = {"youtube-dl.exe", "-f", formats, "-x", "--audio-format", "mp3", input, "--output", path};
            execute(commands);
        }
    }
    
    synchronized private void execute(String[] commands) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(commands);
            StringBuffer buffer = new StringBuffer();
            InputStream stream = proc.getInputStream();
            extractor = new StreamProcessExtractor(buffer, stream);
            Thread thread = new Thread(extractor);
            thread.start();
        } catch (IOException ex) {
            Logger.getLogger(DLThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    synchronized public StreamProcessExtractor extractor(){
        return extractor;
    }
}
