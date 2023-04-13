package com.zcore.mabokeserver.drive;

import org.springframework.data.annotation.Id;

public class Drive {
    @Id
    private Long id;
    //@Column(name = "name", nullable = false)
    private String name;

    public Drive(){}

    public Drive(String name) {
        this.name = name;
    }
    
    public Drive(Long id, String name) {
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
