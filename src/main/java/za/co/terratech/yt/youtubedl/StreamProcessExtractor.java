/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.terratech.yt.youtubedl;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StreamProcessExtractor implements Runnable {

    private static final String GROUP_PERCENT = "percent";
    private static final String GROUP_MINUTES = "minutes";
    private static final String GROUP_SECONDS = "seconds";
    private static final String GROUP_SPEED = "speed";
    private static final String GROUP_SIZE = "size";
    private final InputStream stream;
    private final StringBuffer buffer;
    double progress;
    String eta;
    String speed;
    String size;
    boolean status;

    private final Pattern p = Pattern.compile("(\\[download\\]\\s+)(?<percent>\\d+\\.\\d)(%.*\\s+of\\s+)(?<size>.*)(\\s+at\\s+)(?<speed>.*)\\s+ETA\\s+(?<minutes>\\d*):(?<seconds>\\d*)\\s*");

    public StreamProcessExtractor(StringBuffer buffer, InputStream stream) {
        this.stream = stream;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        status = true;
        try {
            StringBuilder currentLine = new StringBuilder();
            int nextChar;
            while ((nextChar = stream.read()) != -1) {
                
                if (nextChar == '\r') {
                    
                    processOutputLine(currentLine.toString());
                    currentLine.setLength(0);
                    continue;
                }
                buffer.append((char) nextChar);
                currentLine.append((char) nextChar);
            }
        } catch (IOException ignored) {
        }
        status = false;
    }

    synchronized private void processOutputLine(String line) {
        Matcher m = p.matcher(line);
        if (m.matches()) {
            progress = Double.parseDouble(m.group(GROUP_PERCENT));
            eta = m.group(GROUP_MINUTES) + ":" + m.group(GROUP_SECONDS);
            speed = m.group(GROUP_SPEED);
            size = m.group(GROUP_SIZE);
        }
    }

    synchronized public double getProgress() {
        return progress;
    }

    synchronized public String getEta() {
        return eta;
    }

    synchronized public String getSpeed() {
        return speed;
    }

    synchronized public boolean status() {
        return status;
    }

    synchronized public String getSize() {
        return size;
    }
    
    

}
