package com.zcore.mabokeserver.video;

import java.util.Date;
import org.springframework.data.annotation.Id;

public class Video {
    @Id
    private Long id;
    private String title;
    private String summary;
    private String url;
    private String length;
    private String censor_rating;
    private Date created_at;
    private Date upDated_at;
}
