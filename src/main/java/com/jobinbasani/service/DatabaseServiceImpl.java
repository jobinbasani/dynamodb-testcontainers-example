package com.jobinbasani.service;

public class DatabaseServiceImpl implements DatabaseService {

    private final String endpointUrl;

    public DatabaseServiceImpl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    @Override
    public void createTable(String tableName) {

    }

    @Override
    public void deleteTable(String tableName) {

    }
}
