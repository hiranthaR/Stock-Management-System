package com.hirantha.models.data.admins;

import org.bson.types.ObjectId;

public class Admin {

    public static final String ROLE_ADMIN = "Admin";
    public static final String ROLE_INPUT = "Input";
    public static final String ROLE_OUTPUT = "OutPut";
    public static final String ROLE_INPUT_OUTPUT = "Input & OutPut";

    private ObjectId _id;
    private String name;
    private String username;
    private String password;
    private int level;

    public Admin(ObjectId id, String name, String username, String password, int level) {
        this._id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.level = level;
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
