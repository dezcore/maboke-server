package com.zcore.mabokeserver.award;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AwardRepository extends MongoRepository<Award, String> {
    
}
