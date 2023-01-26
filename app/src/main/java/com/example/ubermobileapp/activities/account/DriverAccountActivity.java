package com.example.ubermobileapp.activities.account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.inbox.DriverInboxActivity;
import com.example.ubermobileapp.activities.history.DriverRideHistoryActivity;
import com.example.ubermobileapp.activities.home.DriverMainActivity;
import com.example.ubermobileapp.activities.startup.UserLoginActivity;
import com.example.ubermobileapp.services.utils.AuthService;
import com.example.ubermobileapp.tools.Timer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DriverAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_account);

        TextView logOut = findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthService.logout();
                Timer.setInstance();
                startActivity(new Intent(DriverAccountActivity.this, UserLoginActivity.class));
            }
        });

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
}