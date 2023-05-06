package com.zcore.mabokeserver.search;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.zcore.mabokeserver.cast.Cast;
import com.zcore.mabokeserver.director.Director;
import com.zcore.mabokeserver.studio.Studio;
import com.zcore.mabokeserver.video.Video;
@Document
public class Search {
    @Id
    private String id;
    private Director director;
    private Director producer;
    private Studio studio;
    private Cast cast;
    private Video video;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Director getDirector() {
        return director;
    }
    public void setDirector(Director director) {
        this.director = director;
    }
    public Director getProducer() {
        return producer;
    }
    public void setProducer(Director producer) {
        this.producer = producer;
    }
    public Studio getStudio() {
        return studio;
    }
    public void setStudio(Studio studio) {
        this.studio = studio;
    }
    public Cast getCast() {
        return cast;
    }
    public void setCast(Cast cast) {
        this.cast = cast;
    }
    public Video getVideo() {
        return video;
    }
    public void setVideo(Video video) {
        this.video = video;
    }
    @Override
    public String toString() {
        return "Search [id=" + id + ", director=" + director + ", producer=" + producer + ", studio=" + studio
                + ", cast=" + cast + ", video=" + video + "]";
    }

    
}
