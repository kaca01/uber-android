package com.example.ubermobileapp.services;
import com.example.ubermobileapp.model.LoginRequest;
import com.example.ubermobileapp.model.LoginResponse;
import com.example.ubermobileapp.model.User.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("user/login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @Headers("Content-Type: application/json")
    @GET("currentUserAndroid/{email}")
    Call<String> getCurrentUser(@Path("email") String email);
}
