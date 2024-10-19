package com.mortgageBank.mortgageRequestsIdGenerator.config;

import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Value("${DB_NAME}")
    private String dbName;
    @Value("${MONGODB_URL}")
    private String mongoURL;
    @Value("${MONGODB_PORT}")
    private String mongoPort;

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(MongoClients.create(
                String.format("mongodb://%s:%s/%s", mongoURL, mongoPort, dbName)),
                dbName
        );
    }
}
