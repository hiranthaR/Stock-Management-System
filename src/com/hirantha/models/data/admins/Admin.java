package com.hirantha.models.data.admins;

import org.bson.types.ObjectId;

public class Admin {

    public static final String ROLE_ADMIN = "Admin";
    public static final String ROLE_INPUT = "Input";
    public static final String ROLE_OUTPUT = "OutPut";
    public static final String ROLE_INPUT_OUTPUT = "Input & OutPut";

    private String _id;
    private String name;
    private String username;
    private String password;
    private int level;

    public Admin(String _id, String name, String username, String password, int level) {
        this._id = _id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.level = level;
    }

    public static String getRoleAdmin() {
        return ROLE_ADMIN;
    }

    public static String getRoleInput() {
        return ROLE_INPUT;
    }

    public static String getRoleOutput() {
        return ROLE_OUTPUT;
    }

    public static String getRoleInputOutput() {
        return ROLE_INPUT_OUTPUT;
    }

    public String getId() {
        return _id;
    }

    public Admin setId(String _id) {
        this._id = _id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Admin setName(String name) {
        this.name = name;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Admin setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Admin setPassword(String password) {
        this.password = password;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public Admin setLevel(int level) {
        this.level = level;
        return this;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", level=" + level +
                '}';
    }
}
