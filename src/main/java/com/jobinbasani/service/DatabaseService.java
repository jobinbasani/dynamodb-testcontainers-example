package com.jobinbasani.service;

import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.jobinbasani.vo.TableResponse;

import java.util.List;

public interface DatabaseService {
    TableResponse createTable(String tableName, List<KeySchemaElement> keys, List<AttributeDefinition> attributes, ProvisionedThroughput throughput);
    TableResponse deleteTable(String tableName);
}
