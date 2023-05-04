package com.zcore.mabokeserver.subscription;

import org.springframework.data.repository.CrudRepository;

import com.zcore.mabokeserver.user.User;

public interface SubscriptionRepository extends CrudRepository<User, Long> {
    
}
