package com.zcore.mabokeserver.studio;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudioRepository extends MongoRepository<Studio, String> {
    
}
