package com.example.ubermobileapp.activities.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.account.PassengerAccountActivity;
import com.example.ubermobileapp.activities.inbox.PassengerInboxActivity;
import com.example.ubermobileapp.activities.history.PassengerRideHistoryActivity;
import com.example.ubermobileapp.fragments.home.MapFragment;
import com.example.ubermobileapp.tools.FragmentTransition;
import com.example.ubermobileapp.tools.Timer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PassengerMainActivity extends AppCompatActivity {
    AlertDialog alertDialog;

    private final Timer timer = new Timer();

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

        writeOnClickListeners();
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    private void createDriverDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(PassengerMainActivity.this);

        LayoutInflater inflater = this.getLayoutInflater();
        View newView = inflater.inflate(R.layout.fragment_passengers_info, null);

        dialog.setView(newView)
                .setTitle("Driver Info")
                .setCancelable(true)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alertDialog = dialog.create();
        alertDialog.show();
    }

    private void writeOnClickListeners() {
        CardView passengers = findViewById(R.id.firstCard);
        passengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDriverDialog();
            }
        });

        CardView panic = findViewById(R.id.secondCard);
        panic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : implement sending notification
                backToCurrentLocation();
                timer.onClickReset();
            }
        });
    }

    private void backToCurrentLocation() {
        FragmentTransition.to(MapFragment.newInstance(), this, false);
    }
}