package com.zcore.mabokeserver.video;

import nonapi.io.github.classgraph.json.Id;

public class Video {
    @Id
    private Long id;
    private String content; 


    public Video() {}

    public Video(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Video [id=" + id + ", content=" + content + "]";
    }
}
