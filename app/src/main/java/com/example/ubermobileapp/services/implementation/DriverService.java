package com.example.ubermobileapp.services.implementation;

import android.os.StrictMode;

import com.example.ubermobileapp.models.pojo.ride.Ride;
import com.example.ubermobileapp.models.pojo.ride.RideList;
import com.example.ubermobileapp.models.pojo.user.Passenger;
import com.example.ubermobileapp.models.pojo.user.User;
import com.example.ubermobileapp.services.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DriverService {

    public static List<Ride> getRides(Long id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<Ride> rides = new ArrayList<>();
        Call<RideList> rideResponseCall = ApiUtils.getDriverService().getRides(id);
        try{
            Response<RideList> response = rideResponseCall.execute();
            rides = response.body().getResults();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return rides;
    }

    public static User getDriver(Long id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        User driver = new User();
        Call<User> responseCall = ApiUtils.getDriverService().getDriver(id);
        try{
            Response<User> response = responseCall.execute();
            driver = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return driver;
    }

    public static User updateDriver(User passenger, Long id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        User newPass = new Passenger();
        Call<User> responseCall = ApiUtils.getDriverService().updateDriver(passenger, id);
        try{
            Response<User> response = responseCall.execute();
            newPass = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return newPass;
    }
}
