/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.terratech.yt.model;

/**
 *
 * @author riazs
 */
public class PlaylistEntry {
    public String ie_key;
    public Object view_count;
    public double duration;
    public Object description;
    public String uploader;
    public String _type;
    public String title;
    public String id;
    public String url;

    public String getIe_key() {
        return ie_key;
    }

    public void setIe_key(String ie_key) {
        this.ie_key = ie_key;
    }

    public Object getView_count() {
        return view_count;
    }

    public void setView_count(Object view_count) {
        this.view_count = view_count;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getType() {
        return _type;
    }

    public void setType(String _type) {
        this._type = _type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PlaylistEntry{" + "ie_key=" + ie_key + ", view_count=" + view_count + ", duration=" + duration + ", description=" + description + ", uploader=" + uploader + ", _type=" + _type + ", title=" + title + ", id=" + id + ", url=" + url + '}';
    }
    
    
}
