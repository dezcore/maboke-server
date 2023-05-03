package com.zcore.mabokeserver.comedy;

import org.springframework.data.annotation.Id;

public class Comedy {
    @Id
    private Long id;
    private String name;

    public Comedy(){}

    public Comedy(String name) {
        this.name = name;
    }
    
    public Comedy(Long id, String name) {
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
