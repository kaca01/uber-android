package com.example.ubermobileapp.services.implementation;

import android.content.Context;
import android.os.StrictMode;

import com.example.ubermobileapp.model.login.User;
import com.example.ubermobileapp.model.passenger.Passenger;
import com.example.ubermobileapp.services.utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Response;

public class DriverService {
    public static User getDriver(Long id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        User user = new User();
        Call<User> responseCall = ApiUtils.getDriverService().getDriver(id);
        try{
            Response<User> response = responseCall.execute();
            user = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return user;
    }
}
