package com.hirantha.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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
            String ip = reader.readLine();
            MongoClient client = new MongoClient(ip);
            db = client.getDatabase("stock-management-db");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            new Alert(Alert.AlertType.ERROR, "Error while connecting to the database!", ButtonType.OK).showAndWait();
            System.exit(1);
        }

        return db;
    }
}
