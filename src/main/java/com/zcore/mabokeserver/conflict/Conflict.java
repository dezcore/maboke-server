package com.zcore.mabokeserver.conflict;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;
@Document
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Conflict {
    @Id
    private String id;
    private String orignTitle;
    private String conflictTitle;
}
