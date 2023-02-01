package com.example.ubermobileapp.services;

import com.example.ubermobileapp.models.pojo.GenericList;
import com.example.ubermobileapp.models.pojo.ride.Ride;
import com.example.ubermobileapp.models.pojo.ride.FavoriteOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IRideService {
    //active ride for driver
    @Headers("Content-Type: application/json")
    @GET("ride/driver/{driverId}/active")
    Call<Ride> getDriverActiveRide(@Path("driverId") Long id);

    //active ride for passenger
    @Headers({"Content-Type:application/json"})
    @GET("ride/passenger/{passengerId}/active")
    Call<Ride> getPassengerActiveRide(@Path("passengerId") Long id);

    @Headers({"Content-Type:application/json"})
    @GET("ride/{id}")
    Call<Ride> getRideDetails(@Path("id") Long id);

    @Headers({"Content-Type:application/json"})
    @PUT("ride/{id}/start")
    Call<Ride> startRide(@Path("id") Long id);

    @Headers({"Content-Type:application/json"})
    @PUT("ride/{id}/end")
    Call<Ride> endRide(@Path("id") Long id);
    
    @POST("ride")
    Call<Ride> insertRide(@Body Ride ride);

    @Headers({"Content-Type:application/json"})
    @GET("ride/favorites")
    Call<GenericList<FavoriteOrder>> getFavorites();

    @Headers({"Content-Type:application/json"})
    @POST("ride/favorites")
    Call<FavoriteOrder> addFavorite(@Body FavoriteOrder favoriteOrder);

    @Headers({"Content-Type:application/json"})
    @DELETE("ride/favorites/{id}")
    Call<Boolean> deleteFavorite(@Path("id") Long id);

    @Headers({"Content-Type:application/json"})
    @GET("ride/pending/{id}")
    Call<Ride> getPendingRide(@Path("id") Long id);
}
