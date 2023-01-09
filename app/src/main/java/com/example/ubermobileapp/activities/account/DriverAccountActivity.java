package com.example.ubermobileapp.activities.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.inbox.DriverInboxActivity;
import com.example.ubermobileapp.activities.history.DriverRideHistoryActivity;
import com.example.ubermobileapp.activities.home.DriverMainActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

public class DriverAccountActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_account);
        
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setSelectedItemId(R.id.page_account);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_map:
                        startActivity(new Intent(DriverAccountActivity.this, DriverMainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.page_history:
                        startActivity(new Intent(DriverAccountActivity.this, DriverRideHistoryActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.page_inbox:
                        startActivity(new Intent(DriverAccountActivity.this, DriverInboxActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.page_account:
                        return true;
                }
                return false;
            }
        });



    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(DriverAccountActivity.this, DriverMainActivity.class));
        overridePendingTransition(0,0);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}