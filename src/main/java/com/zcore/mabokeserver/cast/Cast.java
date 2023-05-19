package com.zcore.mabokeserver.cast;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zcore.mabokeserver.actor.Actor;
import com.zcore.mabokeserver.award.Award;

import lombok.Data;
import lombok.ToString;

@Document
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cast {
    @Id
    private String id;
    private List<Actor> actors;
    private List<Award> awards;
}
