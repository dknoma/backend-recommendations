package com.the.mild.project.db.mongo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.the.mild.project.MongoCollections;
import com.the.mild.project.MongoDatabaseType;

public final class MongoDatabaseFactory {
    // "mongodb+srv://<username>:<password>@<cluster name>.mongodb.net/<database name>";
    private static final String CONNECTION_STRING_FORMAT = "mongodb+srv://%s:%s@%s.mongodb.net/%s";
    private static final Map<MongoDatabaseType, DatabaseProxy> DATABASE_BY_PROXY = new HashMap<>();

    private final String username;
    private final String password;

    public MongoDatabaseFactory() {
        final MongoEnv env = new MongoEnv();
        final String cluster = env.getMongoCluster();

        this.username = env.getMongoUser();
        this.password = env.getMongoPass();

        Arrays.stream(MongoDatabaseType.values())
              .filter(type -> type != MongoDatabaseType.NULL)
              .forEach(type -> addDatabase(type, cluster));
    }

    private void addDatabase(MongoDatabaseType type, String clusterName) {
        assert type != MongoDatabaseType.NULL;

        DATABASE_BY_PROXY.put(type, new DatabaseProxy(clusterName, type));
    }

    public MongoDatabase getDatabase(MongoDatabaseType type) {
        assert DATABASE_BY_PROXY.containsKey(type);

        return DATABASE_BY_PROXY.get(type).database;
    }

    private final class DatabaseProxy {
        private final MongoDatabaseType type;
        private final String clusterName;
        private final String databaseName;

        private final ConnectionString connectionString;
        private final MongoDatabase database;

        private DatabaseProxy(String clusterName, MongoDatabaseType type) {
            this.clusterName = clusterName;
            this.type = type;
            this.databaseName = type.databaseName();
            this.connectionString = new ConnectionString(
                String.format(CONNECTION_STRING_FORMAT, username, password, clusterName, databaseName));
            this.database = initDatabase();
        }

        private MongoDatabase initDatabase() {
            final MongoClientSettings settings = MongoClientSettings.builder()
                                                                    .applyConnectionString(connectionString)
                                                                    .retryWrites(true)
                                                                    .writeConcern(WriteConcern.MAJORITY)
                                                                    .build();

            final MongoClient mongoClient = MongoClients.create(settings);
            final MongoDatabase db = mongoClient.getDatabase(databaseName);

            initCollections(db);

            return db;
        }

        private void initCollections(MongoDatabase db) {
            final MongoIterable<String> collectionNames = db.listCollectionNames();
            final Set<String> collectionNamesSet = new HashSet<>();
            collectionNames.forEach((Consumer<String>) collectionNamesSet::add);

            Arrays.stream(MongoCollections.values())
                  .filter(type -> type != MongoCollections.NULL)
                  .forEach(type -> {
                      final String name = type.collectionName();

                      if(!collectionNamesSet.contains(name)) {
                          System.out.printf("Creating collection \"%s\"\n", name);
                          collectionNamesSet.add(name);
                          db.createCollection(name);
                      }
                  });
        }
    }
}
