package com.jobinbasani;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import com.jobinbasani.service.DatabaseService;
import com.jobinbasani.service.DatabaseServiceImpl;

import java.util.List;

public class DatabaseRunner {

    private final DatabaseService databaseService;

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            System.err.println("Valid arguments <action> <tablename>");
            return;
        }
        String action = args[0];
        String tableName = args[1];
        new DatabaseRunner().runMain(action, tableName);
    }

    public DatabaseRunner() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion("us-east-2")
                .build();
        databaseService = new DatabaseServiceImpl(client);
    }

    public void runMain(String action, String tableName) throws InterruptedException {
        switch (action.toLowerCase()) {
            case "create":
                databaseService.createTable(tableName, List.of(new KeySchemaElement("year", KeyType.HASH)),
                        List.of(new AttributeDefinition("year", ScalarAttributeType.S)),
                        new ProvisionedThroughput(1L, 1L));
                System.out.println("Created table " + tableName);
                break;
            case "delete":
                databaseService.deleteTable(tableName);
                System.out.println("Deleted table " + tableName);
                break;
        }
    }
}
