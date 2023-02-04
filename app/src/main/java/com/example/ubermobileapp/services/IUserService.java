package com.example.ubermobileapp.services;
import com.example.ubermobileapp.models.pojo.login.LoginRequest;
import com.example.ubermobileapp.models.pojo.login.LoginResponse;
import com.example.ubermobileapp.models.pojo.login.ResetPassword;
import com.example.ubermobileapp.models.pojo.user.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    @Headers("Content-Type: application/json")
    @GET("user/{email}/resetPassword")
    Call<Boolean> sendMail(@Path("email") String email);

    @Headers("Content-Type: application/json")
    @PUT("user/{email}/resetPassword")
    Call<Boolean> resetPassword(@Path("email") String email, @Body ResetPassword resetPassword);
}
