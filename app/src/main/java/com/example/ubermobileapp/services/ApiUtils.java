package com.example.ubermobileapp.services;

public class ApiUtils {

    public static final String BASE_URL = "http://192.168.100.103:8081/api/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    // instancirati sve ostale servise ovdje i odavde ih pozivati pri koriscenju (jedna instanca)
}
