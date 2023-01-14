package com.example.ubermobileapp.services.implementation;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.example.ubermobileapp.model.pojo.Ride;
import com.example.ubermobileapp.services.utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RideService {

    private Ride getPassengerActiveRide(Context context, Long id){
        final Ride[] ride = new Ride[1];
        Call<Ride> rideResponseCall = ApiUtils.getRideService().getPassengerActiveRide(id);
        rideResponseCall.enqueue(new Callback<Ride>() {
            @Override
            public void onResponse(Call<Ride> call, Response<Ride> response) {
                if (response.isSuccessful()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ride[0] = response.body();
                        }
                    }, 700);

                } else
                    Toast.makeText(context, "Current ride not found", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Ride> call, Throwable t) {
                Toast.makeText(context, "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return ride[0];
    }
}
