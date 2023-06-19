package com.zcore.mabokeserver.conflict;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


public interface ConflictRepository extends ReactiveMongoRepository<Conflict, String> {
    Mono<Boolean> existsByOrignTitleAndConflictTitle(String orignTitle, String conflictTitle);
    Mono<Conflict> findByOrignTitleAndConflictTitle(String orignTitle, String conflictTitle);
}
