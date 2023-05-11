package com.zcore.mabokeserver.movie;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
public interface MovieRepository extends ReactiveMongoRepository<Movie, String> {
    
}
