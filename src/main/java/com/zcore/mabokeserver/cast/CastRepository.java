package com.zcore.mabokeserver.cast;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
public interface CastRepository extends ReactiveMongoRepository<Cast, String> {
    
}
