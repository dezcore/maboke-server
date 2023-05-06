package com.zcore.mabokeserver.view;

import org.springframework.data.mongodb.repository.MongoRepository;
public interface ViewRepository extends MongoRepository<View, String> {
    
}
