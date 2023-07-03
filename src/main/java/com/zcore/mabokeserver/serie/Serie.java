package com.zcore.mabokeserver.serie;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zcore.mabokeserver.cast.Cast;
import com.zcore.mabokeserver.director.Director;
import com.zcore.mabokeserver.season.Season;
import com.zcore.mabokeserver.studio.Studio;

import lombok.Data;
import lombok.ToString;
@Document
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Serie {
    @Id
    private String id;
    private String state;
    private String title;
    private String img;
    private String category;
    private String summary;
    private String gender;
    private String country;
    private boolean hide;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime year;
    private Director director;
    private Director producer;
    private Studio studio;
    private Cast cast;
    private List<String> contentTags;
    private List<Season> seasons;

    public String toJson() {
        int index = 0;
        String json = "{"+
            "\"state\":\""+ state + "\","+
            "\"title\":\""+ title + "\","+
            "\"img\":\""+ img + "\","+
            "\"category\":\""+ category + "\","+
            "\"summary\":\""+ summary + "\","+
            "\"gender\":\""+ gender + "\","+
            "\"country\":\""+ country + "\","+
            "\"hide\":\""+ hide + "\","+
            "\"year\":\""+ year + "\","+
            "\"seasons\":[";

        for(Season season: seasons) {
            json += season.toJson();
            index++;
            if(index < seasons.size())
                json += ",";
        }
        
        json += "]}";
        return json;
    }
}
