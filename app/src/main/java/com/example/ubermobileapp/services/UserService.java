package com.example.ubermobileapp.services;

import android.util.JsonToken;

import com.example.ubermobileapp.model.LoginRequest;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("user/login")
    Call<ResponseBody> userLogin(@Body LoginRequest loginRequest);
}
