package com.zcore.mabokeserver.director;

import java.sql.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
public class Director {
    @Id
    private String id;
    private String name;
    private Date yearStart;
    private Date yearend;
    private Date birthyear;
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
    public Date getYearStart() {
        return yearStart;
    }
    public void setYearStart(Date yearStart) {
        this.yearStart = yearStart;
    }
    public Date getYearend() {
        return yearend;
    }
    public void setYearend(Date yearend) {
        this.yearend = yearend;
    }
    public Date getBirthyear() {
        return birthyear;
    }
    public void setBirthyear(Date birthyear) {
        this.birthyear = birthyear;
    }
    @Override
    public String toString() {
        return "Director [id=" + id + ", name=" + name + ", yearStart=" + yearStart + ", yearend=" + yearend
                + ", birthyear=" + birthyear + "]";
    }
}
