package com.zcore.mabokeserver.season;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Season {
    @Id
    private Long id;
    private String name;
    private String category;
    private String summary;
    private int number;
}
