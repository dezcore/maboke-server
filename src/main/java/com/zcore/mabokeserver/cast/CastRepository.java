package com.zcore.mabokeserver.cast;

import org.springframework.data.mongodb.repository.MongoRepository;
public interface CastRepository extends MongoRepository<Cast, String> {
    
}
