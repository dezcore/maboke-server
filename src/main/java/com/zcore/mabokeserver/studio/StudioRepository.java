package com.zcore.mabokeserver.studio;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface StudioRepository extends ReactiveMongoRepository<Studio, String> {
    
}
