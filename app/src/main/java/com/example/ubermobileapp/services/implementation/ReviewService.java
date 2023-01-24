package com.example.ubermobileapp.services.implementation;

import android.os.StrictMode;

import com.example.ubermobileapp.models.pojo.communication.Review;
import com.example.ubermobileapp.models.pojo.communication.ReviewList;
import com.example.ubermobileapp.services.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ReviewService {

    public static List<ReviewList> getRideReviews(Long id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<ReviewList> rides = new ArrayList<>();
        Call<List<ReviewList>> rideResponseCall = ApiUtils.getReviewService().getRideReviews(id);
        try{
            Response<List<ReviewList>> response = rideResponseCall.execute();
            rides = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return rides;
    }
}
