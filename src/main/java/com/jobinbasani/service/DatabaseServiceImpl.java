package com.jobinbasani.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.jobinbasani.vo.TableResponse;

import java.util.List;

public class DatabaseServiceImpl implements DatabaseService {

    private final DynamoDB dynamoDB;

    public DatabaseServiceImpl(AmazonDynamoDB client) {
        dynamoDB = new DynamoDB(client);
    }

    @Override
    public TableResponse createTable(String tableName, List<KeySchemaElement> keys, List<AttributeDefinition> attributes, ProvisionedThroughput throughput) {
        TableResponse response = new TableResponse();
        try {
            Table table = dynamoDB.createTable(tableName, keys, attributes, throughput);
            table.waitForActive();
            response.setSuccess(true);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public TableResponse deleteTable(String tableName) {
        TableResponse response = new TableResponse();
        try {
            Table table = dynamoDB.getTable(tableName);
            table.delete();
            table.waitForDelete();
            response.setSuccess(true);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
