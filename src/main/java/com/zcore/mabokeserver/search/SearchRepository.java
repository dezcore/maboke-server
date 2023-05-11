package com.zcore.mabokeserver.search;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SearchRepository extends ReactiveMongoRepository<Search, String> {
    
}
