package com.zcore.mabokeserver.season;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SeasonRepository extends MongoRepository<Season, String> {
    
}
