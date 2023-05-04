package com.zcore.mabokeserver.movie;

import org.springframework.data.annotation.Id;
import com.zcore.mabokeserver.cast.Cast;
import com.zcore.mabokeserver.director.Director;
import com.zcore.mabokeserver.studio.Studio;
import com.zcore.mabokeserver.video.Video;

public class Movie {
    @Id
    private Long id;
    private Director director;
    private Director producer;
    private Studio studio;
    private Cast cast;
    private Video video;
}
