package com.jobinbasani;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import com.jobinbasani.service.DatabaseService;
import com.jobinbasani.service.DatabaseServiceImpl;
import com.jobinbasani.vo.TableResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DatabaseRunner {

    private final DatabaseService databaseService;
    private static final Logger logger = LoggerFactory.getLogger(DatabaseRunner.class);

    public static void main(String[] args) {
        if (args.length != 2) {
            logger.error("Valid arguments <action> <tablename>");
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
            case "create" -> {
                response = databaseService.createTable(tableName, List.of(new KeySchemaElement("year", KeyType.HASH)),
                        List.of(new AttributeDefinition("year", ScalarAttributeType.S)),
                        new ProvisionedThroughput(1L, 1L));
                if (response.isSuccess()) {
                    logger.info(tableName+" created!");
                } else {
                    logger.error("Error creating " + tableName + " - " + response.getMessage());
                }
            }
            case "delete" -> {
                response = databaseService.deleteTable(tableName);
                if (response.isSuccess()) {
                    logger.info(tableName + " deleted!");
                } else {
                    logger.error("Error deleting " + tableName + " - " + response.getMessage());
                }
            }
            default -> logger.error("Invalid action specified. Valid options are - create, delete");
        }
    }
}
