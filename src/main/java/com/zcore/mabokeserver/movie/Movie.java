package com.zcore.mabokeserver.movie;

import org.springframework.data.annotation.Id;

public class Movie {
    @Id
    private Long id;
    private String name;
    
    public Movie(){}

    public Movie(String name) {
        this.name = name;
    }
    
    public Movie(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Drive [id=" + id + ", name=" + name + "]";
    }
}
