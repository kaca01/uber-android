package com.example.ubermobileapp.services.utils;

import com.example.ubermobileapp.services.IDriverService;
import com.example.ubermobileapp.services.IMessageService;
import com.example.ubermobileapp.services.IPassengerService;
import com.example.ubermobileapp.services.IRideService;
import com.example.ubermobileapp.services.IUserService;

public class ApiUtils {

    public static final String BASE_URL = "http://192.168.37.86:8081/api/";

    public static IUserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(IUserService.class);
    }

    public static IMessageService getMessageService(){
        return RetrofitClient.getClient(BASE_URL).create(IMessageService.class);
    }
    
    public static IRideService getRideService(){
        return RetrofitClient.getClient(BASE_URL).create(IRideService.class);
    }

    public static IPassengerService getPassengerService() {
        return  RetrofitClient.getClient(BASE_URL).create(IPassengerService.class);
    }

    public static IDriverService getDriverService() {
        return  RetrofitClient.getClient(BASE_URL).create(IDriverService.class);
    }

    // instancirati sve ostale servise ovdje i odavde ih pozivati pri koriscenju
}
