package com.hirantha.admins;

import com.hirantha.models.data.admins.Admin;

public class Permissions {

    public static int ADD_CUSTOMER = 1;
    public static int ADD_ITEM = 1 << 1;
    public static int ROLE_OUTPUT = 1 << 2;
    public static int ROLE_INPUT = 1 << 3;
    public static int ROLE_ADMIN = 1 << 4;

    public static boolean checkPermission(int level, int permission) {
        return (level & permission) == permission;
    }

    public static String getRole(int level) {
        if ((level & ROLE_ADMIN) == ROLE_ADMIN) return Admin.ROLE_ADMIN;
        if ((level & ROLE_INPUT) == ROLE_INPUT && (level & ROLE_OUTPUT) == ROLE_OUTPUT) return Admin.ROLE_INPUT_OUTPUT;
        if ((level & ROLE_INPUT) == ROLE_INPUT) return Admin.ROLE_INPUT;
        if ((level & ROLE_OUTPUT) == ROLE_OUTPUT) return Admin.ROLE_OUTPUT;
        return null;
    }
}
