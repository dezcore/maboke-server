package com.zcore.mabokeserver.award;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

@Document
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Award {
    @Id
    private String id;
    private String name;
    private String organization;
    private String contry;
}
