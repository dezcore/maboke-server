package com.zcore.mabokeserver.season;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.zcore.mabokeserver.video.Video;
@Document
public class Season {
    @Id
    private String id;
    private String title;
    private Date date;
    private String summary;
    private int number;
    private List<Video> videos;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public List<Video> getVideos() {
        return videos;
    }
    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
    @Override
    public String toString() {
        return "Season [id=" + id + ", title=" + title + ", date=" + date + ", summary=" + summary + ", number="
                + number + ", videos=" + videos + "]";
    }

    
}
