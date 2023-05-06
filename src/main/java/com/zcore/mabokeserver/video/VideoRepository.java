package com.zcore.mabokeserver.video;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video, String> {
        
}
