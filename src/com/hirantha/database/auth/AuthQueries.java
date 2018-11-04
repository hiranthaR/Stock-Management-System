package com.hirantha.database.auth;

import com.google.gson.Gson;
import com.hirantha.database.Connection;
import com.hirantha.models.data.admins.Admin;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class AuthQueries {

    private static AuthQueries instance;

    private AuthQueries() {
    }

    public static AuthQueries getInstance() {
        if (instance == null) instance = new AuthQueries();
        return instance;
    }

    private Connection con = Connection.getInstance();
    private MongoDatabase db = con.getDatabase();
    private Gson gson = new Gson();

    private String CUSTOMERS_COLLECTION = "admins";
    private MongoCollection<Document> adminsMongoCollection = db.getCollection(CUSTOMERS_COLLECTION);

    private String PASSWORD = "password";
    private String USERNAME = "username";

    public Admin getAdmin(String username, String password) {

        List<Admin> admins = new ArrayList<>();

        MongoCursor<Document> adminsResults = adminsMongoCollection.find(Filters.and(Filters.eq(USERNAME, username), Filters.eq(PASSWORD, password))).iterator();
        adminsResults.forEachRemaining(document -> admins.add(gson.fromJson(document.toJson(), Admin.class)));

        if (admins.size() == 0) return null;
        return admins.get(0);
    }
}
