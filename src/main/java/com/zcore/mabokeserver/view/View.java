package com.zcore.mabokeserver.view;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document
public class View {
    @Id
    private String id;
    private String user_id;
    private String video_id;
    private String watched_till;
    @DateTimeFormat(iso = ISO.DATE)
    @LastModifiedDate
    private Date updated_at;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getVideo_id() {
        return video_id;
    }
    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }
    public String getWatched_till() {
        return watched_till;
    }
    public void setWatched_till(String watched_till) {
        this.watched_till = watched_till;
    }
    public Date getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "View [user_id=" + user_id + ", video_id=" + video_id + ", watched_till=" + watched_till
                + ", updated_at=" + updated_at + "]";
    }
}
