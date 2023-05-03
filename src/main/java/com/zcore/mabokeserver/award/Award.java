package com.zcore.mabokeserver.award;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Award {
    @Id
    private Long id;
    private String name;
    private String organization;
    private String contry; 
}
