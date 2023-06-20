package com.zcore.mabokeserver.serie;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
public interface SerieRepository extends ReactiveMongoRepository<Serie, String> {
    public Mono<Long> countByState(String state);
    public Flux<Serie> findAllByState(String state);
}
