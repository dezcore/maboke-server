package com.zcore.mabokeserver.video;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Video {
    @Id
    private String id;
    private String title;
    private String category;
    private String summary;
    private String url;
    private String length;
    private String censor_rating;
    private Date created_at;
    private Date upDated_at;

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
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getLength() {
        return length;
    }
    public void setLength(String length) {
        this.length = length;
    }
    public String getCensor_rating() {
        return censor_rating;
    }
    public void setCensor_rating(String censor_rating) {
        this.censor_rating = censor_rating;
    }
    public Date getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
    public Date getUpDated_at() {
        return upDated_at;
    }
    public void setUpDated_at(Date upDated_at) {
        this.upDated_at = upDated_at;
    }
    @Override
    public String toString() {
        return "Video [id=" + id + ", title=" + title + ", category=" + category + ", summary=" + summary + ", url="
                + url + ", length=" + length + ", censor_rating=" + censor_rating + ", created_at=" + created_at
                + ", upDated_at=" + upDated_at + "]";
    }
    
    
}
