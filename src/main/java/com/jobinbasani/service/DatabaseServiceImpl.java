package com.jobinbasani.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

import java.util.List;

public class DatabaseServiceImpl implements DatabaseService {

    private final DynamoDB dynamoDB;

    public DatabaseServiceImpl(AmazonDynamoDB client) {
        dynamoDB = new DynamoDB(client);
    }

    @Override
    public void createTable(String tableName, List<KeySchemaElement> keys, List<AttributeDefinition> attributes, ProvisionedThroughput throughput) throws InterruptedException {
        Table table = dynamoDB.createTable(tableName, keys, attributes, throughput);
        table.waitForActive();
        System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());
    }

    @Override
    public void deleteTable(String tableName) throws InterruptedException {
        Table table = dynamoDB.getTable(tableName);
        table.delete();
        table.waitForDelete();
        System.out.println(tableName + "deleted!");
    }
}
