package com.zcore.mabokeserver.serie;

import org.springframework.data.mongodb.repository.MongoRepository;
public interface SerieRepository extends MongoRepository<Serie, String> {
    
}
