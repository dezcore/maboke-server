package com.zcore.mabokeserver.season;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SeasonRepository extends ReactiveMongoRepository<Season, String> {
    
}
