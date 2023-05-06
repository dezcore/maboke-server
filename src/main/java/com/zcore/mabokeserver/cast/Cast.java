package com.zcore.mabokeserver.cast;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.zcore.mabokeserver.actor.Actor;
import com.zcore.mabokeserver.award.Award;

@Document
public class Cast {
    @Id
    private String id;
    private List<Actor> actors;
    private List<Award> awards;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<Actor> getActors() {
        return actors;
    }
    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
    public List<Award> getAwards() {
        return awards;
    }
    public void setAwards(List<Award> awards) {
        this.awards = awards;
    }
    @Override
    public String toString() {
        return "Cast [id=" + id + ", actors=" + actors + ", awards=" + awards + "]";
    }

    
}
