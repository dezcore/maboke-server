package com.zcore.mabokeserver.subscription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {
    private SubscriptionRepository driveRepository;
    private Logger logger = LoggerFactory.getLogger(SubscriptionService.class);
    
    public SubscriptionService(SubscriptionRepository driveRepository) {
        this.driveRepository = driveRepository;
    }
}
