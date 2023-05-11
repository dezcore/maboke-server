package com.zcore.mabokeserver.view;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ViewRepository extends ReactiveMongoRepository<View, String> {
        
}
