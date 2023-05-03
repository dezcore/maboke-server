package com.zcore.mabokeserver.director;

import java.sql.Date;

import org.springframework.data.annotation.Id;

public class Director {
    @Id
    private Long id;
    private String name;
    private Date yearStart;
    private Date yearend;
    private Date birthyear;
}
