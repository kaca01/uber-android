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
import android.widget.ToggleButton;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.account.DriverAccountActivity;
import com.example.ubermobileapp.activities.inbox.DriverInboxActivity;
import com.example.ubermobileapp.activities.history.DriverRideHistoryActivity;
import com.example.ubermobileapp.fragments.home.DrawRouteFragment;
import com.example.ubermobileapp.fragments.home.MapMainFragment;
import com.example.ubermobileapp.tools.FragmentTransition;
import com.example.ubermobileapp.tools.Timer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.gms.maps.model.LatLng;

public class DriverMainActivity extends AppCompatActivity {
    private boolean play = false;
    AlertDialog alertDialog;

    private Timer timer = new Timer();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        timer.setTextView(findViewById(R.id.timer));

        ToggleButton toggleButton = findViewById(R.id.toggleButton);
        toggleButton.setChecked(true);

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

        writeOnClickListeners();

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

    private void writeOnClickListeners() {
        CardView passengers = findViewById(R.id.firstCard);
        passengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPassengersDialog();
            }
        });

        CardView start_pause = findViewById(R.id.secondCard);
        start_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!play) {
                    play = true;
                    drawRoute();
                    timer.onClickStart();
                } else {
                    play = false;
                    timer.onClickStop();
                }
            }
        });

        start_pause.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                play = false;
                backToCurrentLocation();
                timer.onClickReset();
                return true;
            }
        });

        CardView panic = findViewById(R.id.thirdCard);
        panic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : implement sending notification
                play = false;
                backToCurrentLocation();
                timer.onClickReset();
            }
        });
    }

    private void drawRoute() {
        // TODO : here we should get current (upcoming ride) from database
        // and send departure and destination
        LatLng departure = new LatLng(45.15106698674585, 17.26420725907422);
        LatLng destination = new LatLng(45.145497841538536, 17.27150286700908);
        FragmentTransition.to(DrawRouteFragment.newInstance(departure, destination), this, false);
    }

    private void backToCurrentLocation() {
        FragmentTransition.to(MapMainFragment.newInstance(), this, false);
    }

    private void createPassengersDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(DriverMainActivity.this);

        LayoutInflater inflater = this.getLayoutInflater();
        View newView = inflater.inflate(R.layout.fragment_passengers_info, null);

        dialog.setView(newView)
                .setTitle("Passengers Info")
                .setCancelable(true)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alertDialog = dialog.create();
        alertDialog.show();
    }
}