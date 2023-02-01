package com.example.ubermobileapp.androidService;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;

import com.example.ubermobileapp.activities.home.DriverMainActivity;
import com.example.ubermobileapp.activities.startup.UserLoginActivity;
import com.example.ubermobileapp.models.pojo.ride.Ride;
import com.example.ubermobileapp.models.pojo.user.User;
import com.example.ubermobileapp.services.implementation.RideService;
import com.example.ubermobileapp.services.utils.AuthService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AcceptingRideService extends Service {

    public static String RESULT_CODE = "RESULT_CODE";

    ExecutorService executor = Executors.newSingleThreadExecutor(); // kreira samo jedan thread
    Handler handler = new Handler(Looper.getMainLooper());

    public AcceptingRideService() { }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        /*
         * Primer poziva asinhronog zadatka ako ima veze ka mrezi
         * npr. sinhronizacija mail-ova fotografija, muzike dokumenata isl.
         * */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        User current = AuthService.getCurrentUser();
//        if(ride != null){
//            executor.execute(() -> {
                        //Background work here
//                        try {
//                            Thread.sleep(1000);
                System.out.println("treeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeed");
//                            Intent ints = new Intent(UserLoginActivity.SYNC_DATA);
//                            int intsStatus = 1; // true
//                            ints.putExtra(RESULT_CODE, intsStatus); // salje se u main activity rezultat
//                            getApplicationContext().sendBroadcast(ints);


//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Ride ride = RideService.getPendingRide(current.getId());
                        System.out.println("serviiiiiiiiiiiiiiiiiiiisssssssssssssssssssssssss");
                        System.out.println(ride);
                        if(ride != null) {
                            System.out.println("radi radi radi");
                            Intent ints = new Intent(DriverMainActivity.SYNC_DATA);
                            int intsStatus = 1; // true
                            ints.putExtra(RESULT_CODE, intsStatus); // salje se u main activity rezultat
                            getApplicationContext().sendBroadcast(ints);
                        }

                        handler.postDelayed(this, 5000);
                    }

                });
//            });
//        }

        /*
         * Ako iz nekog razloga operativni sistem ubije servis
         * ne kreirati novi.
         * */
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("biiiiiiiiiiiiiiiiiiiiiiiiiiiiiinddddddddddddddddddddd");
        return null;
    }
}