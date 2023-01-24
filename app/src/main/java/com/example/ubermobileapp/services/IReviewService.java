package com.example.ubermobileapp.services;

import com.example.ubermobileapp.models.pojo.communication.ReviewList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IReviewService {

    @Headers("Content-Type: application/json")
    @GET("review/{rideId}")
    Call<List<ReviewList>> getRideReviews(@Path("rideId") Long rideId);

//    @Headers({"Content-Type:application/json"})
//    @POST("user/{id}/message")
//    Call<Message> sendMessage(@Path("id") Long id, @Body Message message);
}
