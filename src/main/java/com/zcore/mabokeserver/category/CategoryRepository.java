package com.zcore.mabokeserver.category;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
    Flux<Category> findAll();
    Mono<Boolean> existsByCategory(String category);
    Mono<Category> findByCategory(String category);
}
