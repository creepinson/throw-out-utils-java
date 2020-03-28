package me.creepinson.creepinoutils.api.util.data;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * @author creepinson https://gitlab.com/creepinson
 */
public class MongoConfig {
    private MongoClient mongoClient;
    private MongoDatabase db;
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
        db = client().getDatabase(database);
    }

    public void reset() {
        if (mongoClient != null) {
            mongoClient.close();
        }
        mongoClient = null;
        db = null;
    }


    public MongoClient client() {
        return (mongoClient);
    }

    public MongoDatabase db() {
        return (db);
    }

}