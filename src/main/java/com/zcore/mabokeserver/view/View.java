package com.zcore.mabokeserver.view;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

@Document
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class View {
    @Id
    private String id;
    private String user_id;
    private String video_id;
    private String watched_till;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    @CreatedDate
    private LocalDateTime create_date;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    @LastModifiedDate
    private LocalDateTime updated_at;
}
