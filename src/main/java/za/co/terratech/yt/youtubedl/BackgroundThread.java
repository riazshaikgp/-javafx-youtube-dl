/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.terratech.yt.youtubedl;

/**
 *
 * @author riazs
 */
public class BackgroundThread extends Thread {

    YoutubeDLService service;
    String url;
    boolean status = false;

    public BackgroundThread(YoutubeDLService service, String url) {
        this.service = service;
        this.url=url;
    }

    @Override
    public void run() {
        service.checkFormats(url);
        status=true;
    }

    synchronized public YoutubeDLService getService() {
        return service;
    }

    synchronized public boolean isWorkDone() {
        return status;
    }

    synchronized public void setWorking(boolean status) {
        this.status = status;
    }
    
    
}
