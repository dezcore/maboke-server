package com.zcore.mabokeserver.category;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;
@Document
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {
    @Id
    private String id;
    private String page;
    private String category;
}
