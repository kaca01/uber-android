package com.example.ubermobileapp.activities.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.account.PassengerAccountActivity;
import com.example.ubermobileapp.activities.inbox.PassengerInboxActivity;
import com.example.ubermobileapp.activities.history.PassengerRideHistoryActivity;
import com.example.ubermobileapp.activities.receiver.NotificationReceiver;
import com.example.ubermobileapp.androidService.AcceptedRideService;
import com.example.ubermobileapp.fragments.home.CreateRide1Fragment;
import com.example.ubermobileapp.fragments.home.CreateRide2Fragment;
import com.example.ubermobileapp.fragments.home.CreateRide3Fragment;
import com.example.ubermobileapp.fragments.home.map.MapMainFragment;
import com.example.ubermobileapp.models.RideOrder;
import com.example.ubermobileapp.models.enumeration.RideStatus;
import com.example.ubermobileapp.models.pojo.ride.Ride;
import com.example.ubermobileapp.models.pojo.user.User;
import com.example.ubermobileapp.services.implementation.RideService;
import com.example.ubermobileapp.services.utils.AuthService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


public class PassengerMainActivity extends AppCompatActivity {

    CardView back;
    AppCompatButton cancelButton;
    TextView timer;
    CardView timerCard;
    public static int currentFragment;
    public static Geocoder geocoder;
    public static RideOrder order;

    Fragment fragment1 = new CreateRide1Fragment();
    Fragment fragment2 = new CreateRide2Fragment();
    Fragment fragment3 = new CreateRide3Fragment();

    public static String ACCEPTED_DATA = "ACCEPTED_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_main);

        Locale locale = new Locale("sr", "RS");
        geocoder = new Geocoder(this, locale);
        order = new RideOrder();
        back = findViewById(R.id.backCard);
        cancelButton = findViewById(R.id.cancel_order);
        timer = findViewById(R.id.timer);
        timerCard = findViewById(R.id.timer_card);

        checkForPendingRide();
        checkForAcceptedRide();
        checkForActiveRide();
        checkIfRideIsRejected();

        createNotificationChannel();

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentFragment==1) {
                    changeToFirstFragment();
                }
                else if (currentFragment==2) {
                    changeToSecondFragment();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(view.getContext(), "Ride canceled!", Toast.LENGTH_LONG);
                toast.show();
                RideService.cancelRideByPassenger(order.getRideId());
                refreshActivity();
            }
        });

        if (currentFragment == -1) {
            setCancelButtonVisible();
            findViewById(R.id.fragment_container).setVisibility(View.GONE);
        }
    }

    public void createTimer(){
        new CountDownTimer(15000, 1000) {
            public void onTick(long millisUntilFinished) {
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;

                timer.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));

            }
            public void onFinish() {
                // todo dodaj notifikaciju da je stiglo vozilo
                timer.setText("00:00:00");
            }
        }.start();
    }

    public void checkIfRideIsRejected(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean stop = false;
                Ride activeRide;
                if (order.getRideId() != null && AuthService.getCurrentUser() != null){
                    activeRide = RideService.getRideDetails(order.getRideId());
                    if ( activeRide != null && activeRide.getStatus().equals(RideStatus.REJECTED.toString())){
                        stop = true;
                        refreshActivity();
                    }
                }

                if (!stop) {
                    handler.postDelayed(this, 3000);
                }
            }
        }, 3000);
    }

    public void checkForActiveRide(){
        User user = AuthService.getCurrentUser();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean stop = false;
                Ride activeRide;
                try {
                    if (user != null){
                        activeRide = RideService.getPassengerActiveRide(user.getId());
                        if ( activeRide != null){
                            stop = true;
                            refreshActivity();
                            startActivity(new Intent(PassengerMainActivity.this, PassengerCurrentRideActivity.class));
                            overridePendingTransition(0,0);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!stop) {
                    handler.postDelayed(this, 3000);
                }
            }
        }, 3000);
    }

    public void checkForAcceptedRide(){
        User user = AuthService.getCurrentUser();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean stop = false;
                Ride thisRide;
                try {
                    if (user != null){
                        thisRide = RideService.getAcceptedRide(user.getId());
                        if (thisRide != null){
                            stop = true;
                            timerCard.setVisibility(View.VISIBLE);
                            setCancelButtonVisible();
                            findViewById(R.id.fragment_container).setVisibility(View.GONE);
                            createTimer();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!stop) {
                    handler.postDelayed(this, 2000);
                }
            }
        }, 2000);
    }

    public void checkForPendingRide(){
        User user = AuthService.getCurrentUser();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean stop = false;
                Ride thisRide;
                try {
                    if (user != null) {
                        thisRide = RideService.getPassengerPendingRide(user.getId());
                        if (thisRide != null) {
                            stop = true;
                            setCancelButtonVisible();
                            findViewById(R.id.fragment_container).setVisibility(View.GONE);
                            setBackButtonInvisible();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!stop) {
                    handler.postDelayed(this, 2000);
                }
            }
        }, 2000);
    }

    public static Ride insertRide() {
        currentFragment = -1;

        Ride ride = new Ride(order);
        ride = RideService.insertRide(ride);
        order.setRideId(ride.getId());
        return ride;
    }

    public void changeToFirstFragment()
    {
        currentFragment = 0;
        back.setVisibility(View.GONE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment1, null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void changeToSecondFragment()
    {
        currentFragment = 1;
        back.setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment2, null);
        fragmentTransaction.commit();
    }

    public void changeToThirdFragment()
    {
        currentFragment = 2;
        back.setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment3, null);
        fragmentTransaction.commit();
    }

    public void refreshActivity()
    {
        cancelButton.setVisibility(View.GONE);
        timerCard.setVisibility(View.GONE);
        MapMainFragment.destinationString = null;
        MapMainFragment.departureString = null;
        findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
        changeToFirstFragment();
    }

    public void setCancelButtonVisible(){
        cancelButton.setVisibility(View.VISIBLE);
    }

    public void setBackButtonInvisible(){
        back.setVisibility(View.GONE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (fragment1.isAdded())
        getSupportFragmentManager().putFragment(outState, "fragment1", fragment1);
        if (fragment2.isAdded())
        getSupportFragmentManager().putFragment(outState, "fragment2", fragment2);
        if (fragment3.isAdded())
        getSupportFragmentManager().putFragment(outState, "fragment3", fragment3);

        outState.putInt("currentFragment", currentFragment);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            //Restore the fragment's instance
            fragment1 = getSupportFragmentManager().getFragment(savedInstanceState, "fragment1");
            fragment2 = getSupportFragmentManager().getFragment(savedInstanceState, "fragment2");
            fragment3 = getSupportFragmentManager().getFragment(savedInstanceState, "fragment3");

            currentFragment = savedInstanceState.getInt("currentFragment");
        }
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}