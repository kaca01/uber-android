package com.example.ubermobileapp.services;

import com.example.ubermobileapp.models.pojo.ride.RideList;
import com.example.ubermobileapp.models.pojo.user.Passenger;
import com.example.ubermobileapp.models.pojo.user.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IPassengerService {

    @Headers("Content-Type: application/json")
    @GET("passenger/{id}")
    Call<Passenger> getPassenger(@Path("id") Long id);

    @Headers({"Content-Type:application/json"})
    @GET("passenger/{id}/ride")
    Call<RideList> getRides(@Path("id") Long id);

    @Headers({"Content-Type:application/json"})
    @POST("passenger")
    Call<Passenger> insertPassenger(@Body Passenger passenger);

    @Headers({"Content-Type:application/json"})
    @PUT("passenger/{id}")
    Call<Passenger> updatePassenger(@Body User passenger, @Path("id") Long id);
}
