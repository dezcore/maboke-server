package com.zcore.mabokeserver.movie;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zcore.mabokeserver.cast.Cast;
import com.zcore.mabokeserver.director.Director;
import com.zcore.mabokeserver.studio.Studio;
import com.zcore.mabokeserver.video.Video;

import lombok.Data;
import lombok.ToString;
@Document
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    @Id
    private String id;
    private Director director;
    private Director producer;
    private Studio studio;
    private Cast cast;
    private Video video;
}
