package com.zcore.mabokeserver.serie;

import org.springframework.data.annotation.Id;

public class Serie {
    @Id
    private Long id;
    private String name;

    public Serie(){}

    public Serie(String name) {
        this.name = name;
    }
    
    public Serie(Long id, String name) {
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
