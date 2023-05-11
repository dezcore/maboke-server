package com.zcore.mabokeserver.actor;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ActorRepository extends ReactiveMongoRepository<Actor, String> {
    
}
