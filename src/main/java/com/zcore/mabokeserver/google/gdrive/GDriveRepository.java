package com.zcore.mabokeserver.google.gdrive;

import java.util.List;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GDriveRepository extends ReactiveMongoRepository<GDrive, String> {
    public Mono<GDrive> findByName(String name);
    public Mono<Boolean> existsByName(String name);
    public Flux<GDrive> findAllByName(String name);
    public Mono<Void> deleteAllById(List<String> ids);
    public Flux<GDrive> findByNameIn(List<String> names);
}
