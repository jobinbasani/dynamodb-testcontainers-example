package com.jobinbasani;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import com.jobinbasani.service.DatabaseService;
import com.jobinbasani.service.DatabaseServiceImpl;
import com.jobinbasani.vo.TableResponse;

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

    public void runMain(String action, String tableName) {
        TableResponse response;
        switch (action.toLowerCase()) {
            case "create":
                response = databaseService.createTable(tableName, List.of(new KeySchemaElement("year", KeyType.HASH)),
                        List.of(new AttributeDefinition("year", ScalarAttributeType.S)),
                        new ProvisionedThroughput(1L, 1L));
                if (response.isSuccess()) {
                    System.out.printf("%s created!", tableName);
                } else {
                    System.out.printf("Error creating %s - %s", tableName, response.getMessage());
                }
                break;
            case "delete":
                response = databaseService.deleteTable(tableName);
                if (response.isSuccess()) {
                    System.out.printf("%s deleted!", tableName);
                } else {
                    System.out.printf("Error deleting %s - %s", tableName, response.getMessage());
                }
                break;
            default:
                System.err.println("Invalid action specified. Valid options are - create, delete");
                break;
        }
    }
}
