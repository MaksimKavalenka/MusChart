package com.muschart.constants;

public abstract class ServiceConstants {

    private static final String BASE_URL = "http://localhost:8081";
    private static final String SERVICE_URL = "/service";

    public static final String TRACK_SERVICE = BASE_URL + SERVICE_URL + "/tracks";
    public static final String USER_SERVICE = BASE_URL + SERVICE_URL + "/users";

}