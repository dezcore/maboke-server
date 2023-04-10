package com.zcore.mabokeserver.model;

public class Video {
    private long id;
    private String content; 

    public Video(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Video [id=" + id + ", content=" + content + "]";
    }

    
}
