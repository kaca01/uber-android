package com.example.ubermobileapp.services.implementation;

import android.os.StrictMode;
import android.content.Context;

import com.example.ubermobileapp.models.pojo.GenericList;
import com.example.ubermobileapp.models.pojo.communication.Rejection;
import com.example.ubermobileapp.models.pojo.ride.FavoriteOrder;
import com.example.ubermobileapp.models.pojo.ride.Ride;
import com.example.ubermobileapp.services.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RideService {

    public static Ride getPassengerActiveRide(Long id) throws InterruptedException {
        final Ride[] ride = {new Ride()};
        Thread thread = new Thread(){
            public void run() {
                Call<Ride> rideResponseCall = ApiUtils.getRideService().getPassengerActiveRide(id);
                try {
                    Response<Ride> response = rideResponseCall.execute();
                    ride[0] = response.body();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        thread.start();
        thread.join();
        return ride[0];
    }

    public static Ride getDriverActiveRide(Long id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Ride ride = new Ride();
        Call<Ride> rideResponseCall = ApiUtils.getRideService().getDriverActiveRide(id);
        try{
            Response<Ride> response = rideResponseCall.execute();
            ride = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return ride;
    }

    public static Ride getRideDetails(Long id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Ride ride = new Ride();
        Call<Ride> rideResponseCall = ApiUtils.getRideService().getRideDetails(id);
        try{
            Response<Ride> response = rideResponseCall.execute();
            ride = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return ride;
    }

    public static Ride start(Context context, Long id, String toastText){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Ride ride = new Ride();
        Call<Ride> rideResponseCall = ApiUtils.getRideService().startRide(id);
        try{
            Response<Ride> response = rideResponseCall.execute();
            ride = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return ride;
    }

    public static Ride end(Context context, Long id, String toastText){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Ride ride = new Ride();
        Call<Ride> rideResponseCall = ApiUtils.getRideService().endRide(id);
        try{
            Response<Ride> response = rideResponseCall.execute();
            ride = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return ride;
    }

    public static Ride insertRide(Ride ride){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Call<Ride> rideResponseCall = ApiUtils.getRideService().insertRide(ride);
        try{
            Response<Ride> response = rideResponseCall.execute();
            ride = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return ride;
    }

    public static FavoriteOrder insertFavoriteLocation(FavoriteOrder order){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Call<FavoriteOrder> rideResponseCall = ApiUtils.getRideService().insertFavoriteOrder(order);
        try{
            Response<FavoriteOrder> response = rideResponseCall.execute();
            order = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return order;
    }


    public static List<FavoriteOrder> getFavorites() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<FavoriteOrder> favoriteOrders = new ArrayList<>();

        Call<GenericList<FavoriteOrder>> rideResponseCall = ApiUtils.getRideService().getFavorites();
        try{
            Response<GenericList<FavoriteOrder>> response = rideResponseCall.execute();
            favoriteOrders = response.body().getResults();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return favoriteOrders;
    }

    public static FavoriteOrder addFavorite(FavoriteOrder favoriteOrder){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Call<FavoriteOrder> rideResponseCall = ApiUtils.getRideService().addFavorite(favoriteOrder);
        try{
            Response<FavoriteOrder> response = rideResponseCall.execute();
            favoriteOrder = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return favoriteOrder;
    }

    public static Ride getPendingRide(Long id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Ride ride = new Ride();
        Call<Ride> rideResponseCall = ApiUtils.getRideService().getPendingRide(id);
        try{
            Response<Ride> response = rideResponseCall.execute();
            ride = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return ride;
    }

    public static Ride getPassengerPendingRide(Long id) throws InterruptedException {
        final Ride[] ride = {new Ride()};
        Thread thread = new Thread(){
            public void run() {
                Call<Ride> rideResponseCall = ApiUtils.getRideService().getPassengerPendingRide(id);
                try {
                    Response<Ride> response = rideResponseCall.execute();
                    ride[0] = response.body();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        thread.start();
        thread.join();
        return ride[0];
    }

    public static Ride acceptRide(Long id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Ride ride = new Ride();
        Call<Ride> rideResponseCall = ApiUtils.getRideService().acceptRide(id);
        try{
            Response<Ride> response = rideResponseCall.execute();
            ride = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return ride;
    }

    public static Ride cancelRide(Long id, Rejection rejection){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Ride ride = new Ride();
        Call<Ride> rideResponseCall = ApiUtils.getRideService().cancelRide(id, rejection);
        try{
            Response<Ride> response = rideResponseCall.execute();
            ride = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return ride;
    }

    public static Ride cancelRideByPassenger(Long id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Ride ride = new Ride();
        Call<Ride> rideResponseCall = ApiUtils.getRideService().cancelRideByPassenger(id);
        try{
            Response<Ride> response = rideResponseCall.execute();
            ride = response.body();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return ride;
    }

    public static Ride getAcceptedRide(Long id) throws InterruptedException {
        final Ride[] ride = {new Ride()};
        Thread thread = new Thread(){
            public void run() {
                Call<Ride> rideResponseCall = ApiUtils.getRideService().getAcceptedRide(id);
                try {
                    Response<Ride> response = rideResponseCall.execute();
                    ride[0] = response.body();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        thread.start();
        thread.join();
        return ride[0];
    }
}
