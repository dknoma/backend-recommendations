package com.the.mild.project;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum MongoDatabaseType {
    DEVELOP_TEST("develop_test"),
    NULL("");

    private static final Map<String, MongoDatabaseType> DATABASE_BY_NAME = new HashMap<>();

    static {
        Arrays.asList(MongoDatabaseType.values())
              .forEach(database -> DATABASE_BY_NAME.put(database.databaseName, database));
    }

    private String databaseName;

    MongoDatabaseType(String databaseName) {
        this.databaseName = databaseName;
    }

    public String databaseName() {
        return this.databaseName;
    }

    public static MongoDatabaseType getByName(String name) {
        return DATABASE_BY_NAME.getOrDefault(name, NULL);
    }
}
