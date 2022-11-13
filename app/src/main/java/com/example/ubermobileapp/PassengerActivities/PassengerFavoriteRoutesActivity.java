package com.example.ubermobileapp.PassengerActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ubermobileapp.DriverActivities.DriverAccountActivity;
import com.example.ubermobileapp.DriverActivities.DriverInboxActivity;
import com.example.ubermobileapp.DriverActivities.DriverMainActivity;
import com.example.ubermobileapp.DriverActivities.DriverRideHistoryActivity;
import com.example.ubermobileapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PassengerFavoriteRoutesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_favorite_routes);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setSelectedItemId(R.id.page_account);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_map:
                        startActivity(new Intent(PassengerFavoriteRoutesActivity.this, PassengerMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_history:
                        return true;
                    case R.id.page_inbox:
                        startActivity(new Intent(PassengerFavoriteRoutesActivity.this, PassengerInboxActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_account:
                        startActivity(new Intent(PassengerFavoriteRoutesActivity.this, PassengerAccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }


    @Override
    public void onBackPressed(){
        startActivity(new Intent(PassengerFavoriteRoutesActivity.this, PassengerAccountActivity.class));
        overridePendingTransition(0,0);
    }
}