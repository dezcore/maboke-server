package com.zcore.mabokeserver.drive;

import org.springframework.data.annotation.Id;

public class Drive {
    @Id
    private Long id;

    public Drive(){}

    public Drive(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
}
