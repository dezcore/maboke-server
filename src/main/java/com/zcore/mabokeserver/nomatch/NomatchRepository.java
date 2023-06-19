package com.zcore.mabokeserver.nomatch;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface NomatchRepository extends ReactiveMongoRepository<Nomatch, String> {
    Mono<Nomatch> findByTitle(String title);
}
