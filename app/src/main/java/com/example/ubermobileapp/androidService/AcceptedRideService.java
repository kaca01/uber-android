package com.example.ubermobileapp.androidService;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.StrictMode;

import com.example.ubermobileapp.activities.home.PassengerMainActivity;
import com.example.ubermobileapp.models.pojo.ride.Ride;
import com.example.ubermobileapp.models.pojo.user.User;
import com.example.ubermobileapp.services.implementation.RideService;
import com.example.ubermobileapp.services.utils.AuthService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AcceptedRideService extends Service {

    public static String RESULT_CODE = "RESULT_CODE";

    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    public AcceptedRideService() { }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String rideId = intent.getStringExtra("rideId");

        executor.execute(new Runnable() {
            @Override
            public void run() {
//                System.out.println("serviiiiiiiiiiiiiiiiiiiiiiiis");
//                Ride pendingRide = RideService.getPendingRide(current.getId());
//                System.out.println(pendingRide);
//                if(pendingRide != null) {
//                    Ride ride = RideService.getRideDetails(pendingRide.getId());
                Ride ride = RideService.getRideDetails(Long.parseLong(rideId));
                    System.out.println("paseeeeeeeeeeeeeeeeeeengeeeeeeeeeeeeeeeeeeeeeeer");
                    System.out.println(ride);
                    if (ride.getStatus().equals("ACCEPTED")) {
                        System.out.println("prihvacenooooo");
                        Intent ints = new Intent(PassengerMainActivity.ACCEPTED_DATA);
                        int intsStatus = 1; // true
                        ints.putExtra(RESULT_CODE, intsStatus); // salje se u main activity rezultat
                        getApplicationContext().sendBroadcast(ints);
                    }
                    else if(ride.getStatus().equals("REJECTED")) {
                        System.out.println("odbijenooooooooooo");
                        Intent ints = new Intent(PassengerMainActivity.ACCEPTED_DATA);
                        int intsStatus = 0; // false
                        ints.putExtra(RESULT_CODE, intsStatus); // salje se u main activity rezultat
                        getApplicationContext().sendBroadcast(ints);
                    }
//                }
                handler.postDelayed(this, 10000);
            }
        });

        // if for some reason the os kills the service, do not create a new one
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
        handler.removeCallbacksAndMessages(null);
    }
}