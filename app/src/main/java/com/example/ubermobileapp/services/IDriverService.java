package com.example.ubermobileapp.services;

import com.example.ubermobileapp.models.pojo.ride.RideList;
import com.example.ubermobileapp.models.pojo.user.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface IDriverService {

    @Headers({"Content-Type:application/json"})
    @GET("driver/{id}/ride")
    Call<RideList> getRides(@Path("id") Long id);

    @Headers("Content-Type: application/json")
    @GET("driver/{id}")
    Call<User> getDriver(@Path("id") Long id);
}
