package com.zcore.mabokeserver.award;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AwardRepository extends ReactiveMongoRepository<Award, String> {
    
}
