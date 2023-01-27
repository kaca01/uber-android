package com.example.ubermobileapp.activities.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.home.PassengerMainActivity;
import com.example.ubermobileapp.activities.inbox.PassengerInboxActivity;
import com.example.ubermobileapp.activities.history.PassengerRideHistoryActivity;
import com.example.ubermobileapp.activities.startup.UserLoginActivity;
import com.example.ubermobileapp.services.utils.AuthService;
import com.example.ubermobileapp.tools.Timer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class PassengerAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_account);

        addPieChartClickListeners();

        TextView logOut = findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthService.logout();
                Timer.setInstance();
                startActivity(new Intent(PassengerAccountActivity.this, UserLoginActivity.class));
            }
        });

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setSelectedItemId(R.id.page_account);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_map:
                        startActivity(new Intent(PassengerAccountActivity.this, PassengerMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_history:
                        startActivity(new Intent(PassengerAccountActivity.this, PassengerRideHistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_inbox:
                        startActivity(new Intent(PassengerAccountActivity.this, PassengerInboxActivity.class));
                        overridePendingTransition(0,0);
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
        startActivity(new Intent(PassengerAccountActivity.this, PassengerMainActivity.class));
        overridePendingTransition(0,0);
    }

    public void addPieChartClickListeners() {
        TextView first = findViewById(R.id.first);
        TextView second = findViewById(R.id.second);
        TextView third = findViewById(R.id.third);

        CardView firstReport = findViewById(R.id.report1);
        CardView secondReport = findViewById(R.id.report2);
        CardView thirdReport = findViewById(R.id.report3);

        secondReport.setVisibility(View.GONE);
        thirdReport.setVisibility(View.GONE);

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstReport.setVisibility(View.VISIBLE);
                secondReport.setVisibility(View.GONE);
                thirdReport.setVisibility(View.GONE);
                first.setTextColor(Color.parseColor("#96D49C"));
                second.setTextColor(Color.parseColor("#000000"));
                third.setTextColor(Color.parseColor("#000000"));
            }
        });

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstReport.setVisibility(View.GONE);
                secondReport.setVisibility(View.VISIBLE);
                thirdReport.setVisibility(View.GONE);
                first.setTextColor(Color.parseColor("#000000"));
                second.setTextColor(Color.parseColor("#96D49C"));
                third.setTextColor(Color.parseColor("#000000"));
            }
        });

        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstReport.setVisibility(View.GONE);
                secondReport.setVisibility(View.GONE);
                thirdReport.setVisibility(View.VISIBLE);
                first.setTextColor(Color.parseColor("#000000"));
                second.setTextColor(Color.parseColor("#000000"));
                third.setTextColor(Color.parseColor("#96D49C"));
            }
        });
    }
}