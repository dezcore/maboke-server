package com.zcore.mabokeserver.actor;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Actor {
    @Id
    private Long id;
    private String name;
    private Date yearstart;
    private Date yearend;
    private String gender;
    private Date birthyear;
    private Date deathyear;
}
