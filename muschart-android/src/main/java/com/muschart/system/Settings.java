package com.muschart.system;

public class Settings {

    private static int sort = 1;
    private static boolean order = false;

    public static int getSort() {
        return sort;
    }

    public static void setSort(int sort) {
        Settings.sort = sort;
    }

    public static boolean getOrder() {
        return order;
    }

    public static void setOrder(boolean order) {
        Settings.order = order;
    }

}