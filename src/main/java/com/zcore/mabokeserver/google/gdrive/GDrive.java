package com.zcore.mabokeserver.google.gdrive;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class GDrive {
    @Id
    private String id;
    private String fileId;
    private String name;
}
