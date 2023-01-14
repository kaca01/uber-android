package com.example.ubermobileapp.services;

import com.example.ubermobileapp.model.pojo.Ride;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface RideService {

    //active ride for driver
    @Headers("Content-Type: application/json")
    @GET("driver/{driverId}/active")
    Call<Ride> getDriverActiveRide(@Path("driverId") Long id);

    //active ride for passenger
    @Headers({"Content-Type:application/json"})
    @GET("passenger/{passengerId}/active")
    Call<Ride> getPassengerActiveRide(@Path("passengerId") Long id);

}
