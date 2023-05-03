package com.zcore.mabokeserver.cast;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.zcore.mabokeserver.actor.Actor;
import com.zcore.mabokeserver.award.Award;

public class Cast {
    @Id
    private Long id;
    private List<Actor> actors;
    private List<Award> awards;
}
