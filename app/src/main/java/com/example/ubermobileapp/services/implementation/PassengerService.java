package com.example.ubermobileapp.services.implementation;

import android.content.Context;
import android.os.StrictMode;

import com.example.ubermobileapp.model.passenger.Passenger;

import com.example.ubermobileapp.services.utils.ApiUtils;

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
}
