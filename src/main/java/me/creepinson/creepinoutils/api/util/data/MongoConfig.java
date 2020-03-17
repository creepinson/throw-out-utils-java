package me.creepinson.creepinoutils.api.util.data;

import com.mongodb.DB;
import com.mongodb.MongoClient;

/**
 * @author creepinson https://gitlab.com/creepinson
 */
public class MongoConfig {
    private MongoClient mongoClient;
    private DB db;
    private String database;

    public MongoConfig(String database) {
        this.database = database;
    }

    public void connect(String host, int port) {
        try {
            mongoClient = new MongoClient(host, port);
        } catch (Exception e) {
            System.out.println("Couldn't connect to database!.");
        }
        db = getMongoClient().getDB(database);
    }

    public void reset() {
        if (mongoClient != null) {
            mongoClient.close();
        }
        mongoClient = null;
        db = null;
    }


    public MongoClient getMongoClient() {
        return (mongoClient);
    }

    public DB getMongoDB() {
        return (db);
    }

}