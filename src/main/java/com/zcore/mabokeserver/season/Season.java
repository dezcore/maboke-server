package com.zcore.mabokeserver.season;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.zcore.mabokeserver.video.Video;

public class Season {
    @Id
    private Long id;
    private String title;
    private Date date;
    private String summary;
    private int number;
    private List<Video> videos;
}
