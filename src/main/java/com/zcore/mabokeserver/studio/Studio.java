package com.zcore.mabokeserver.studio;

import java.sql.Date;

import org.springframework.data.annotation.Id;

public class Studio {
    @Id
    private Long id;
    private String name;
    private String city;
    private String country;
    private Date yearStart;
    private Date yearend;
    private Date birthyear;
    private String fonder;
}
