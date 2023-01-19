package com.example.ubermobileapp.services;

import com.example.ubermobileapp.models.passenger.Passenger;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface IPassengerService {

    @Headers("Content-Type: application/json")
    @GET("passenger/{id}")
    Call<Passenger> getPassenger(@Path("id") Long id);
}
