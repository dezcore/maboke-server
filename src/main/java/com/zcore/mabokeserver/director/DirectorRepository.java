package com.zcore.mabokeserver.director;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DirectorRepository extends MongoRepository<Director, String> {
    
}
