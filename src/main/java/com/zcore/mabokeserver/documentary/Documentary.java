package com.zcore.mabokeserver.documentary;

import org.springframework.data.annotation.Id;

public class Documentary {
    @Id
    private Long id;
    private String name;

    public Documentary(){}

    public Documentary(String name) {
        this.name = name;
    }
    
    public Documentary(Long id, String name) {
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
