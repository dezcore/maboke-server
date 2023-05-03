package com.zcore.mabokeserver.serie;

import java.util.Date;

import org.springframework.data.annotation.Id;

import com.zcore.mabokeserver.cast.Cast;
import com.zcore.mabokeserver.director.Director;
import com.zcore.mabokeserver.studio.Studio;

public class Serie {
    @Id
    private Long id;
    private String title;
    private String category;
    private String summary;
    private Date year;
    private Director director;
    private Director producer;
    private Studio studio;
    private Cast cast;  
}
