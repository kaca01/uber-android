package com.example.ubermobileapp.services.implementation;

import android.content.Context;
import android.os.Handler;
import android.os.StrictMode;
import android.widget.Toast;

import com.example.ubermobileapp.model.pojo.Ride;
import com.example.ubermobileapp.services.utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RideService {
    public static Ride getPassengerActiveRide(Context context, Long id, String toastText){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Ride ride = new Ride();
        Call<Ride> rideResponseCall = ApiUtils.getRideService().getPassengerActiveRide(id);
        try{
            Response<Ride> response = rideResponseCall.execute();
            ride = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return ride;
    }

    public static Ride getDriverActiveRide(Context context, Long id, String toastText){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Ride ride = new Ride();
        Call<Ride> rideResponseCall = ApiUtils.getRideService().getDriverActiveRide(id);
        try{
            Response<Ride> response = rideResponseCall.execute();
            ride = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return ride;
    }
}
