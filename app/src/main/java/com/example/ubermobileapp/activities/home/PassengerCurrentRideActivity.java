package com.example.ubermobileapp.activities.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.account.PassengerAccountActivity;
import com.example.ubermobileapp.activities.history.PassengerRideHistoryActivity;
import com.example.ubermobileapp.activities.inbox.PassengerInboxActivity;
import com.example.ubermobileapp.receiver.NotificationReceiver;
import com.example.ubermobileapp.fragments.dialogs.LeavingReviewFragment;
import com.example.ubermobileapp.models.enumeration.RideStatus;
import com.example.ubermobileapp.models.pojo.ride.Ride;
import com.example.ubermobileapp.models.pojo.user.User;
import com.example.ubermobileapp.services.implementation.RideService;
import com.example.ubermobileapp.services.utils.AuthService;
import com.example.ubermobileapp.tools.Timer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PassengerCurrentRideActivity extends AppCompatActivity {
    private final Timer timer = Timer.getInstance();
    Ride ride;

    // notifications
    private NotificationReceiver notificationReceiver;
    public static String VEHICLE_DATA = "VEHICLE_DATA";
    private static final String CHANNEL_ID = "One channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_current_ride);
        try {
            ride = RideService.getPassengerActiveRide(AuthService.getCurrentUser().getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.setTextView(findViewById(R.id.timer));

        createNotificationChannel();
        checkIfRideEnded();

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setSelectedItemId(R.id.page_map);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_map:
                        return true;
                    case R.id.page_history:
                        startActivity(new Intent(PassengerCurrentRideActivity.this, PassengerRideHistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_inbox:
                        startActivity(new Intent(PassengerCurrentRideActivity.this, PassengerInboxActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_account:
                        startActivity(new Intent(PassengerCurrentRideActivity.this, PassengerAccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        if (savedInstanceState != null) {

            // Get the previous state of the stopwatch
            // if the activity has been
            // destroyed and recreated.
            timer.setSeconds
                    (savedInstanceState
                            .getInt("seconds"));
            timer.setRunning
                    (savedInstanceState
                            .getBoolean("running"));
            timer.setWasRunning
                    (savedInstanceState
                            .getBoolean("wasRunning"));
        }
        timer.run();

        setUpReceiver();
    }

    public void checkIfRideEnded(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean stop = false;
                Ride thisRide = RideService.getRideDetails(ride.getId());
                if(thisRide != null) {
                    if (thisRide.getStatus().equals(RideStatus.FINISHED.toString())) {
                        stop = true;
                    }
                }
                if (!stop) {
                    handler.postDelayed(this, 3000);
                } else{
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    LeavingReviewFragment leavingReviewFragment = new LeavingReviewFragment(String.valueOf(thisRide.getId()), true);
                    try {
                        leavingReviewFragment.show(fragmentManager, "leaving_review");
                    }catch (IllegalStateException ignored) {
                        // There's no way to avoid getting this if saveInstanceState has already been called.
                    }
                }
            }
        }, 3000);
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public void onSaveInstanceState(
            Bundle savedInstanceState) {
        Ride thisRide = RideService.getRideDetails(ride.getId());
        if(thisRide != null) {
            if (thisRide.getStatus().equals(RideStatus.FINISHED.toString())) {
                return;
            }
        }
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState
                .putInt("seconds", timer.getSeconds());
        savedInstanceState
                .putBoolean("running", timer.isRunning());
        savedInstanceState
                .putBoolean("wasRunning", timer.isWasRunning());
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        timer.setWasRunning(timer.isRunning());
        timer.setRunning(false);
    }

    // If the activity is resumed,
    // start the stopwatch
    // again if it was running previously.
    @Override
    protected void onResume()
    {
        super.onResume();
        if (timer.isWasRunning()) {
            timer.setRunning(true);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification channel";
            String description = "Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setUpReceiver(){
        notificationReceiver = new NotificationReceiver();

        // send filter to receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(VEHICLE_DATA);
        registerReceiver(notificationReceiver, filter);
    }
}