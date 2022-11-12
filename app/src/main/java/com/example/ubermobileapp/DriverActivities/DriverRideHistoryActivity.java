package com.example.ubermobileapp.DriverActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.ubermobileapp.PassengerActivities.PassengerAccountActivity;
import com.example.ubermobileapp.PassengerActivities.PassengerInboxActivity;
import com.example.ubermobileapp.PassengerActivities.PassengerMainActivity;
import com.example.ubermobileapp.PassengerActivities.PassengerRideHistoryActivity;
import com.example.ubermobileapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DriverRideHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_ride_history);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setSelectedItemId(R.id.page_history);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_map:
                        startActivity(new Intent(DriverRideHistoryActivity.this, DriverMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_history:
                        return true;
                    case R.id.page_inbox:
                        startActivity(new Intent(DriverRideHistoryActivity.this, DriverInboxActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_account:
                        startActivity(new Intent(DriverRideHistoryActivity.this, DriverAccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        View map = findViewById(R.id.AllRides1);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverRideHistoryActivity.this, RideInformationActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(DriverRideHistoryActivity.this, DriverMainActivity.class));
        overridePendingTransition(0,0);
    }
}