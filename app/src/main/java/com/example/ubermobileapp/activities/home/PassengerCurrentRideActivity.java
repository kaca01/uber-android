package com.example.ubermobileapp.activities.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.account.PassengerAccountActivity;
import com.example.ubermobileapp.activities.history.PassengerRideHistoryActivity;
import com.example.ubermobileapp.activities.inbox.PassengerInboxActivity;
import com.example.ubermobileapp.tools.Timer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PassengerCurrentRideActivity extends AppCompatActivity {
    private final Timer timer = Timer.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_current_ride);

        timer.setTextView(findViewById(R.id.timer));

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
        timer.run();    }

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
}