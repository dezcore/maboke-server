package com.zcore.mabokeserver.serie;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SerieRepository extends ReactiveMongoRepository<Serie, String> {
}
