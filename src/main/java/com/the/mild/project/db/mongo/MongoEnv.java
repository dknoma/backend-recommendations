package com.the.mild.project.db.mongo;

public final class MongoEnv {
    private static final String MONGO_USER_KEY = "mongoUser";
    private static final String MONGO_PASS_KEY = "mongoPass";
    private static final String MONGO_CLUSTER_KEY = "mongoCluster";
    private static final String MONGO_DATABASE_KEY = "mongoDb";

    private String mongoUser;
    private String mongoPass;
    private String mongoCluster;
    private String mongoDb;

    public MongoEnv() {
        parseEnv();
    }
    
    private void parseEnv() {
        this.mongoUser = System.getenv(MONGO_USER_KEY);
        if (mongoUser == null) {
            mongoUser = "";
        }

        this.mongoPass = System.getenv(MONGO_PASS_KEY);
        if (mongoPass == null) {
            mongoPass = "";
        }

        this.mongoCluster = System.getenv(MONGO_CLUSTER_KEY);
        if (mongoCluster == null) {
            mongoCluster = "";
        }

        this.mongoDb = System.getenv(MONGO_DATABASE_KEY);
        if (mongoDb == null) {
            mongoDb = "";
        }
    }
    
    public String getMongoUser() {
        return mongoUser;
    }
    
    public String getMongoPass() {
        return mongoPass;
    }

    public String getMongoCluster() {
        return mongoCluster;
    }

    public String getMongoDb() {
        return mongoDb;
    }
}
