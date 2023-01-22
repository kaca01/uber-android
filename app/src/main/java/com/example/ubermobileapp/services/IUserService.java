package com.example.ubermobileapp.services;
import com.example.ubermobileapp.models.login.LoginRequest;
import com.example.ubermobileapp.models.login.LoginResponse;
import com.example.ubermobileapp.models.login.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IUserService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("user/login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @Headers("Content-Type: application/json")
    @GET("currentUser")
    Call<User> getCurrentUser();
}
