package com.example.ubermobileapp.activities.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.home.DriverMainActivity;
import com.example.ubermobileapp.androidService.AcceptingRideService;
import com.example.ubermobileapp.models.pojo.communication.Rejection;
import com.example.ubermobileapp.models.pojo.ride.Ride;
import com.example.ubermobileapp.models.pojo.user.User;
import com.example.ubermobileapp.services.implementation.RideService;
import com.example.ubermobileapp.services.utils.AuthService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AcceptanceRideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // stop service
        stopService(new Intent(this, AcceptingRideService.class));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance_ride);

        setRide();

        Button accept = findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo treba da se obrise i da se doda u funkciju acceptRide()
                Intent intent = new Intent(AcceptanceRideActivity.this, DriverMainActivity.class);
                startActivity(intent);
                acceptRide();
            }
        });

        Button rejection = findViewById(R.id.reject);
        rejection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Tell us why");
                final EditText input = new EditText(view.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setHint("Why did you reject the ride...");
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String reason = input.getText().toString().trim();
                        if(reason.equals("")) {
                            Toast toast = Toast.makeText(view.getContext(), "Input field is required!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else {
                            cancelRide(reason);
                            Toast toast = Toast.makeText(view.getContext(), "Thank you for the feedback!", Toast.LENGTH_SHORT);
                            toast.show();
                            Intent intent = new Intent(AcceptanceRideActivity.this, DriverMainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    private Ride getRide() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        User current = AuthService.getCurrentUser();
        return RideService.getPendingRide(current.getId());
    }

    @SuppressLint("SetTextI18n")
    private void setRide() {
        Ride ride = getRide();
        TextView from = findViewById(R.id.from);
        from.setText(ride.getLocations().get(0).getDeparture().getAddress());
        TextView to = findViewById(R.id.to);
        to.setText(ride.getLocations().get(0).getDestination().getAddress());
        TextView cost = findViewById(R.id.cost);
        cost.setText(Double.toString(ride.getTotalCost()));
        TextView passengerNum = findViewById(R.id.passenger_num);
        passengerNum.setText(Integer.toString(ride.getPassengers().size()));
        TextView time = findViewById(R.id.time);
        time.setText(Double.toString(ride.getEstimatedTimeInMinutes()));
    }

    private void acceptRide() {
        Ride ride = getRide();
        Ride acceptRide = RideService.acceptRide(ride.getId());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Intent intent;
            Date scheduledTime = format.parse(acceptRide.getScheduledTime());
//            if(scheduledTime.before(new Date())) {
//                // todo convert to current ride
//                intent = new Intent(AcceptanceRideActivity.this, DriverMainActivity.class);
//            }
//            else
//                intent = new Intent(AcceptanceRideActivity.this, DriverMainActivity.class);
//            startActivity(intent);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void cancelRide(String reason) {
        Ride ride = getRide();
        Rejection rejection = new Rejection(reason);

        Ride cancelRide = RideService.cancelRide(ride.getId(), rejection);
        // todo posalji notifikaciju putniku da mu je voznja odbijena i da proba ponovo
    }
}