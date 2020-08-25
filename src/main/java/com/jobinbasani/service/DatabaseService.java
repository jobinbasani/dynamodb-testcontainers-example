package com.jobinbasani.service;

import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

import java.util.List;

public interface DatabaseService {
    void createTable(String tableName, List<KeySchemaElement> keys, List<AttributeDefinition> attributes, ProvisionedThroughput throughput) throws InterruptedException;
    void deleteTable(String tableName) throws InterruptedException;
}
