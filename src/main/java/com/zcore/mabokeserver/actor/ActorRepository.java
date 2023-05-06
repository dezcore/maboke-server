package com.zcore.mabokeserver.actor;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActorRepository extends MongoRepository<Actor, String> {
    
}
