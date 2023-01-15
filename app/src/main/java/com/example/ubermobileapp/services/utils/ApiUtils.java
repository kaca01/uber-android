package com.example.ubermobileapp.services.utils;

import com.example.ubermobileapp.services.IPassengerService;
import com.example.ubermobileapp.services.IRideService;
import com.example.ubermobileapp.services.IUserService;

public class ApiUtils {

    public static final String BASE_URL = "http://192.168.37.251:8081/api/";

    public static IUserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(IUserService.class);
    }

    public static IRideService getRideService(){
        return RetrofitClient.getClient(BASE_URL).create(IRideService.class);
    }

    public static IPassengerService getPassengerService() {
        return  RetrofitClient.getClient(BASE_URL).create(IPassengerService.class);
    }

    // instancirati sve ostale servise ovdje i odavde ih pozivati pri koriscenju
}
