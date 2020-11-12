package com.example.boba;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;

@EnableMongoRepositories(basePackages = {"com.example.boba.model"})
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${mongo.dbname}")
    private String databaseName;

    @Value("${mongo.connectionString}")
    private String connectionStr;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(connectionStr);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.example.boba.model");
    }
}
