package com.zcore.mabokeserver.video;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

@Document
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Video {
    @Id
    private String id;
    private String img;
    private String title;
    private String category;
    private String summary;
    private String url;
    private String length;
    private String videoId;
    private String censor_rating;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime year;//created_at
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime upDated_at;
    
    public String toJson() {
        return "{"+
            "\"img\":\""+ img + "\","+
            "\"title\":\""+ title + "\","+
            "\"category\":\""+ category + "\","+
            "\"summary\":\""+ summary + "\","+
            "\"url\":\""+ url + "\","+
            "\"length\":\""+ length + "\","+
            "\"videoId\":\""+ videoId + "\","+
            "\"censor_rating\":\""+ censor_rating + "\","+
            "\"year\":\""+ year + "\","+
            "\"upDated_at\":\""+ upDated_at + "\""+
        "}";
    }
}
