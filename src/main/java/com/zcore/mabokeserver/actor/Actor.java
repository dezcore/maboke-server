package com.zcore.mabokeserver.actor;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Actor {
    @Id
    private String id;
    private String name;
    private Date yearstart;
    private Date yearend;
    private String gender;
    private Date birthyear;
    private Date deathyear;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getYearstart() {
        return yearstart;
    }
    public void setYearstart(Date yearstart) {
        this.yearstart = yearstart;
    }
    public Date getYearend() {
        return yearend;
    }
    public void setYearend(Date yearend) {
        this.yearend = yearend;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public Date getBirthyear() {
        return birthyear;
    }
    public void setBirthyear(Date birthyear) {
        this.birthyear = birthyear;
    }
    public Date getDeathyear() {
        return deathyear;
    }
    public void setDeathyear(Date deathyear) {
        this.deathyear = deathyear;
    }
    
    @Override
    public String toString() {
        return "Actor [id=" + id + ", name=" + name + ", yearstart=" + yearstart + ", yearend=" + yearend + ", gender="
                + gender + ", birthyear=" + birthyear + ", deathyear=" + deathyear + "]";
    }

    
}
