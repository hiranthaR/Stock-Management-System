package com.hirantha.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.io.*;

public class Connection {

    private Connection() {
    }

    private static Connection instance;

    public static Connection getInstance() {
        if (instance == null) instance = new Connection();
        return instance;
    }

    public MongoDatabase getDatabase() {
        MongoDatabase db = null;
        File configFile = new File("./config.config");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(configFile));
            MongoClient client = new MongoClient(reader.readLine());
            db = client.getDatabase("stock-management-db");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return db;
    }
}