package com.jobinbasani.service;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@Testcontainers
@TestInstance(PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DatabaseServiceImplTest {

    @Container
    static GenericContainer dynamoDb = new GenericContainer("amazon/dynamodb-local:1.13.2")
            .withCommand("-jar DynamoDBLocal.jar -inMemory -sharedDb")
            .withExposedPorts(8000);
    DatabaseService databaseService;
    String tableName;

    @BeforeAll
    public void init() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:" + dynamoDb.getFirstMappedPort(), "us-west-2"))
                .build();
        databaseService = new DatabaseServiceImpl(client);
        tableName = "TestTable";
    }

    @Test
    @Order(1)
    void createTable() throws InterruptedException {
        databaseService.createTable(tableName, List.of(new KeySchemaElement("year", KeyType.HASH)),
                List.of(new AttributeDefinition("year", ScalarAttributeType.S)),
                new ProvisionedThroughput(1L, 1L));

    }

    @Test
    @Order(2)
    void deleteTable() throws InterruptedException {
        databaseService.deleteTable(tableName);
    }
}