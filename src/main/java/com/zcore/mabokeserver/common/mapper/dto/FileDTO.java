package com.zcore.mabokeserver.common.mapper.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.zcore.mabokeserver.google.gpermission.GPermission;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileDTO {
    private String fileId;
    private String mimeType;
    private String fileName;
    private String folderName;
    private String foldersPaths;
    private String parentFileId;
    private GPermission permission;
    private List<String> filesNames;
    private JsonNode fileContent;
    private List<JsonNode> contents;
}
