package com.zcore.mabokeserver.category;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
    Mono<Category> findByPageAndCategory(String page, String category);
    Mono<Boolean> existsByPageAndCategory(String page, String category);
}
