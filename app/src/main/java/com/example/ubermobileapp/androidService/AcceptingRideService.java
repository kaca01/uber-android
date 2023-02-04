package com.example.ubermobileapp.androidService;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.StrictMode;

import com.example.ubermobileapp.activities.home.DriverMainActivity;
import com.example.ubermobileapp.models.pojo.ride.Ride;
import com.example.ubermobileapp.models.pojo.user.User;
import com.example.ubermobileapp.services.implementation.RideService;
import com.example.ubermobileapp.services.utils.AuthService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AcceptingRideService extends Service {

    public static String RESULT_CODE = "RESULT_CODE";

    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    public AcceptingRideService() { }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        User current = AuthService.getCurrentUser();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Ride ride = RideService.getDriverPendingRide(current.getId());
                System.out.println("serviiiiiiiiiiiiiiiiiiiisssssssssssssssssssssssss");
                System.out.println(ride);
                if (ride != null) {
                    System.out.println("radi radi radi");
                    Intent ints = new Intent(DriverMainActivity.SYNC_DATA);
                    int intsStatus = 1; // true
                    ints.putExtra(RESULT_CODE, intsStatus); // salje se u main activity rezultat
                    getApplicationContext().sendBroadcast(ints);
                }
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