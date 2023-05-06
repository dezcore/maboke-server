package com.zcore.mabokeserver.studio;

import java.sql.Date;

import org.springframework.data.annotation.Id;

public class Studio {
    @Id
    private String id;
    private String name;
    private String city;
    private String country;
    private Date yearStart;
    private Date yearend;
    private Date birthyear;
    private String fonder;

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
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
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
    public String getFonder() {
        return fonder;
    }
    public void setFonder(String fonder) {
        this.fonder = fonder;
    }
    @Override
    public String toString() {
        return "Studio [id=" + id + ", name=" + name + ", city=" + city + ", country=" + country + ", yearStart="
                + yearStart + ", yearend=" + yearend + ", birthyear=" + birthyear + ", fonder=" + fonder + "]";
    }
    
}
