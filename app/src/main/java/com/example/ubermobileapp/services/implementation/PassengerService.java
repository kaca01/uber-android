package com.example.ubermobileapp.services.implementation;

import android.os.StrictMode;

import com.example.ubermobileapp.models.pojo.ride.Ride;
import com.example.ubermobileapp.models.pojo.ride.RideList;
import com.example.ubermobileapp.models.pojo.user.Passenger;

import com.example.ubermobileapp.services.utils.ApiUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PassengerService {

    public static Passenger getPassenger(Long id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Passenger passenger = new Passenger();
        Call<Passenger> responseCall = ApiUtils.getPassengerService().getPassenger(id);
        try{
            Response<Passenger> response = responseCall.execute();
            passenger = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return passenger;
    }

    public static List<Ride> getRides(Long id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<Ride> rides = new ArrayList<>();
        Call<RideList> rideResponseCall = ApiUtils.getPassengerService().getRides(id);
        try{
            Response<RideList> response = rideResponseCall.execute();
            rides = response.body().getResults();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return rides;
    }

    public static Passenger insertPassenger(Passenger passenger){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Call<Passenger> rideResponseCall = ApiUtils.getPassengerService().insertPassenger(passenger);
        try{
            Response<Passenger> response = rideResponseCall.execute();
            passenger = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return passenger;
    }
}
