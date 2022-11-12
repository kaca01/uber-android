package com.example.ubermobileapp.DriverActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.ubermobileapp.PassengerActivities.PassengerRegisterActivity;
import com.example.ubermobileapp.R;
import com.example.ubermobileapp.UserLoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class RideInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_information);

        BottomNavigationView navigation = findViewById(R.id.bottom_nav);
        navigation.setSelectedItemId(R.id.page_history);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_map:
                        startActivity(new Intent(RideInformationActivity.this, DriverMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_history:
                        startActivity(new Intent(RideInformationActivity.this, DriverRideHistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_inbox:
                        startActivity(new Intent(RideInformationActivity.this, DriverInboxActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_account:
                        startActivity(new Intent(RideInformationActivity.this, DriverAccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RideInformationActivity.this, DriverRideHistoryActivity.class));
        overridePendingTransition(0,0);
    }
}