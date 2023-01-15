package com.example.ubermobileapp.services;


import com.example.ubermobileapp.model.login.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface IDriverService {
    @Headers("Content-Type: application/json")
    @GET("passenger/{id}")
    Call<User> getDriver(@Path("id") Long id);
}
