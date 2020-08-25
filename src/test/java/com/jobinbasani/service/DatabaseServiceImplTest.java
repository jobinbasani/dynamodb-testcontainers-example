package com.jobinbasani.service;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@Testcontainers
@TestInstance(PER_CLASS)
class DatabaseServiceImplTest {

    @Container
    static GenericContainer dynamoDb = new GenericContainer("amazon/dynamodb-local:1.13.2")
            .withExposedPorts(8000);
    DatabaseService databaseService;

    @BeforeAll
    public void init() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:" + dynamoDb.getFirstMappedPort(), "us-west-2"))
                .build();
        databaseService = new DatabaseServiceImpl(client);
    }

    @Test
    void createTable() throws InterruptedException {
        databaseService.createTable("TestTable", List.of(new KeySchemaElement("year", KeyType.HASH)),
                List.of(new AttributeDefinition("year", ScalarAttributeType.S)),
                new ProvisionedThroughput(1L, 1L));

    }

    @Test
    void deleteTable() {
        System.out.println(dynamoDb.getContainerId());
    }
}