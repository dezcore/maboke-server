package com.zcore.mabokeserver.award;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Award {
    @Id
    private String id;
    private String name;
    private String organization;
    private String contry;
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
    public String getOrganization() {
        return organization;
    }
    public void setOrganization(String organization) {
        this.organization = organization;
    }
    public String getContry() {
        return contry;
    }
    public void setContry(String contry) {
        this.contry = contry;
    }
    @Override
    public String toString() {
        return "Award [id=" + id + ", name=" + name + ", organization=" + organization + ", contry=" + contry + "]";
    }
    
    
}
