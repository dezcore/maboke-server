package com.zcore.mabokeserver.season;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zcore.mabokeserver.video.Video;

import lombok.Data;
import lombok.ToString;
@Document
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Season {
    @Id
    private String id;
     private String img;
    private String title;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime date;
    private String summary;
    private int number;
    private List<Video> videos;
}
