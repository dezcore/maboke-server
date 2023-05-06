package com.zcore.mabokeserver.search;

import org.springframework.data.mongodb.repository.MongoRepository;
public interface SearchRepository extends MongoRepository<Search, String> {
    
}
