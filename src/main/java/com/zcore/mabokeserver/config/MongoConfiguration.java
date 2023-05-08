package com.zcore.mabokeserver.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public class MongoConfiguration {
    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    Logger logger = LogManager.getLogger(MongoConfiguration.class);

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoClient mongoClient = MongoClients.create();
        return new MongoTemplate(mongoClient, databaseName);
    }
}
