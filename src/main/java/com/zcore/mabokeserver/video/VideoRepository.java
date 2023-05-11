package com.zcore.mabokeserver.video;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VideoRepository extends ReactiveMongoRepository<Video, String> {
        
}
