package com.hirantha.database.admins;

import com.google.gson.Gson;
import com.hirantha.database.Connection;
import com.hirantha.database.meta.MetaQueries;
import com.hirantha.models.data.admins.Admin;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class AdminQueries {

    private Connection con = Connection.getInstance();
    private MongoDatabase db = con.getDatabase();
    private Gson gson = new Gson();

    private String ADMINS_COLLECTION = "admins";
    private MongoCollection<Document> adminsMongoCollection = db.getCollection(ADMINS_COLLECTION);

    private String ID = "_id";
    private String NAME = "name";
    private String PASSWORD = "password";
    private String LEVEL = "level";
    private String USERNAME = "username";

    private static AdminQueries instance;

    private AdminQueries() {
    }

    public static AdminQueries getInstance() {
        if (instance == null) instance = new AdminQueries();
        return instance;
    }


    public void insertAdmin(Admin admin) {
        int id = MetaQueries.getInstance().getAdminNextID();
        Document adminDocument = new Document()
                .append(ID, id)
                .append(NAME, admin.getName())
                .append(USERNAME, admin.getUsername())
                .append(PASSWORD, admin.getPassword())
                .append(LEVEL, admin.getLevel());

        adminsMongoCollection.insertOne(adminDocument);
    }

    public void updateAdmin(Admin admin) {

        BasicDBObject newDataDocument = new BasicDBObject("$set",
                new BasicDBObject(NAME, admin.getName())
                        .append(PASSWORD, admin.getPassword())
                        .append(USERNAME, admin.getUsername())
                        .append(LEVEL, admin.getLevel()));

        adminsMongoCollection.updateOne(Filters.eq(ID, admin.getId()), newDataDocument);

    }

    public List<Admin> getAdmins() {
        List<Admin> admins = new ArrayList<>();

        FindIterable<Document> customersResults = adminsMongoCollection.find();
        customersResults.sort(new BasicDBObject(NAME, 1));
        customersResults.iterator().forEachRemaining(document -> admins.add(gson.fromJson(document.toJson(), Admin.class)));

        return admins;
    }

    public void deleteAdmin(Admin admin) {
        adminsMongoCollection.deleteOne(Filters.eq(ID, admin.getId()));
    }

    public Admin getAdmin(String id) {
        return gson.fromJson(adminsMongoCollection.find(Filters.eq(ID, Integer.parseInt(id))).first().toJson(), Admin.class);
    }

}
