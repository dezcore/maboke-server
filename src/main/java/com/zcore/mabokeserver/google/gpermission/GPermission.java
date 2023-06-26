package com.zcore.mabokeserver.google.gpermission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class GPermission {
    private String fileId;
    private String type;
    private String role;
}
