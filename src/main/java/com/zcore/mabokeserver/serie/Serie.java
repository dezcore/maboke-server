package com.zcore.mabokeserver.serie;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.zcore.mabokeserver.cast.Cast;
import com.zcore.mabokeserver.director.Director;
import com.zcore.mabokeserver.season.Season;
import com.zcore.mabokeserver.studio.Studio;
@Document
public class Serie {
    @Id
    private String id;
    private String title;
    private String img;
    private String category;
    private String summary;
    private Date year;
    private Director director;
    private Director producer;
    private Studio studio;
    private Cast cast;
    private List<String> contentTag;
    private List<Season> seasons;
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
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public Date getYear() {
        return year;
    }
    public void setYear(Date year) {
        this.year = year;
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
    public List<String> getContentTag() {
        return contentTag;
    }
    public void setContentTag(List<String> contentTag) {
        this.contentTag = contentTag;
    }
    public List<Season> getSeasons() {
        return seasons;
    }
    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }
    
    @Override
    public String toString() {
        return "Serie [id=" + id + ", title=" + title + ", img=" + img + ", category=" + category + ", summary="
                + summary + ", year=" + year + ", director=" + director + ", producer=" + producer + ", studio="
                + studio + ", cast=" + cast + ", contentTag=" + contentTag + ", seasons=" + seasons + "]";
    } 
    
}
