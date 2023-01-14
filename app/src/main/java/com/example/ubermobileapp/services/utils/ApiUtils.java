package com.example.ubermobileapp.services.utils;

import com.example.ubermobileapp.services.MessageService;
import com.example.ubermobileapp.services.RideService;
import com.example.ubermobileapp.services.UserService;
import com.example.ubermobileapp.services.utils.RetrofitClient;

public class ApiUtils {

    public static final String BASE_URL = "http://192.168.0.102:8081/api/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    public static MessageService getMessageService(){
        return RetrofitClient.getClient(BASE_URL).create(MessageService.class);
    }

    public static RideService getRideService(){
        return RetrofitClient.getClient(BASE_URL).create(RideService.class);
    }

    // instancirati sve ostale servise ovdje i odavde ih pozivati pri koriscenju
}
