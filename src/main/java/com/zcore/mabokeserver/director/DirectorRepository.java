package com.zcore.mabokeserver.director;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface DirectorRepository extends ReactiveMongoRepository<Director, String> {
    
}
