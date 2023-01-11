package com.example.ubermobileapp.activities.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.account.PassengerAccountActivity;
import com.example.ubermobileapp.activities.inbox.PassengerInboxActivity;
import com.example.ubermobileapp.activities.history.PassengerRideHistoryActivity;
import com.example.ubermobileapp.fragments.home.CreateRide1Fragment;
import com.example.ubermobileapp.fragments.home.CreateRide2Fragment;
import com.example.ubermobileapp.fragments.home.CreateRide3Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PassengerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_main);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setSelectedItemId(R.id.page_map);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_map:
                        return true;
                    case R.id.page_history:
                        startActivity(new Intent(PassengerMainActivity.this, PassengerRideHistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_inbox:
                        startActivity(new Intent(PassengerMainActivity.this, PassengerInboxActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_account:
                        startActivity(new Intent(PassengerMainActivity.this, PassengerAccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    public void changeToSecondFragment()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, CreateRide2Fragment.class, null);
        fragmentTransaction.commit();
    }

    public void changeToThirdFragment()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, CreateRide3Fragment.class, null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}