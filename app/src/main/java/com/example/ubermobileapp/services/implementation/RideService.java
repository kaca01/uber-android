package com.example.ubermobileapp.services.implementation;

import android.os.StrictMode;
import android.content.Context;
import android.os.Handler;
import android.os.StrictMode;
import android.widget.Toast;

import com.example.ubermobileapp.model.pojo.Ride;
import com.example.ubermobileapp.services.utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Response;

public class RideService {

    public static Ride getPassengerActiveRide(Long id){
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

    public static Ride getDriverActiveRide(Long id){
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

    public static Ride getRideDetails(Long id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Ride ride = new Ride();
        Call<Ride> rideResponseCall = ApiUtils.getRideService().getRideDetails(id);
        try{
            Response<Ride> response = rideResponseCall.execute();
            ride = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return ride;
    }

    public static Ride start(Context context, Long id, String toastText){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Ride ride = new Ride();
        Call<Ride> rideResponseCall = ApiUtils.getRideService().startRide(id);
        try{
            Response<Ride> response = rideResponseCall.execute();
            ride = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return ride;
    }

    public static Ride end(Context context, Long id, String toastText){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Ride ride = new Ride();
        Call<Ride> rideResponseCall = ApiUtils.getRideService().endRide(id);
        try{
            Response<Ride> response = rideResponseCall.execute();
            ride = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return ride;
    }

        public static Ride insertRide(Ride ride){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Call<Ride> rideResponseCall = ApiUtils.getRideService().insertRide(ride);
        try{
            Response<Ride> response = rideResponseCall.execute();
            ride = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return ride;
    }
}
