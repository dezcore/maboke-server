package com.zcore.mabokeserver.show;

import org.springframework.data.annotation.Id;

public class Show {
    @Id
    private Long id;
    private String name;

    public Show(){}

    public Show(String name) {
        this.name = name;
    }
    
    public Show(Long id, String name) {
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
