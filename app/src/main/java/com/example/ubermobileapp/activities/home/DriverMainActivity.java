package com.example.ubermobileapp.activities.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.account.DriverAccountActivity;
import com.example.ubermobileapp.activities.inbox.ChatActivity;
import com.example.ubermobileapp.activities.inbox.DriverInboxActivity;
import com.example.ubermobileapp.activities.history.DriverRideHistoryActivity;
import com.example.ubermobileapp.activities.receiver.NotificationReceiver;
import com.example.ubermobileapp.androidService.AcceptingRideService;
import com.example.ubermobileapp.tools.Timer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DriverMainActivity extends AppCompatActivity {

    private Timer timer = Timer.getInstance();

    private NotificationReceiver notificationReceiver;
    public static String SYNC_DATA = "SYNC_DATA";
    private static final String CHANNEL_ID = "Zero channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        timer.setTextView(findViewById(R.id.timer));

        ToggleButton toggleButton = findViewById(R.id.toggleButton);
        toggleButton.setChecked(true);

        createNotificationChannel();

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setSelectedItemId(R.id.page_map);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_map:
                        return true;
                    case R.id.page_history:
                        startActivity(new Intent(DriverMainActivity.this, DriverRideHistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_inbox:
                        startActivity(new Intent(DriverMainActivity.this, DriverInboxActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_account:
                        startActivity(new Intent(DriverMainActivity.this, DriverAccountActivity.class));
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
        Timer.setInstance();
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

        setUpReceiver();

        // send filter to receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(SYNC_DATA);
        registerReceiver(notificationReceiver, filter);
    }

    private void setUpReceiver(){
        notificationReceiver = new NotificationReceiver();
        Intent alarmIntent = new Intent(this, AcceptingRideService.class);
        startService(alarmIntent);
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
}