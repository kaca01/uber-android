package com.example.ubermobileapp.fragments.dialogs;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.models.pojo.ride.Ride;
import com.example.ubermobileapp.models.pojo.user.User;
import com.example.ubermobileapp.services.implementation.RideService;
import com.example.ubermobileapp.services.utils.AuthService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class OrderHistoryRideFragment extends DialogFragment {

    Ride ride;
    int hourOrder;
    int minuteOrder;
    Date orderDate;
    TextView time;

    public OrderHistoryRideFragment(Ride ride) {
        this.ride = ride;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history_ride, container, false);

        // set location
        TextView departure = (TextView) view.findViewById(R.id.departure);
        departure.setText(ride.getLocations().get(0).getDeparture().getAddress());
        TextView destination = (TextView) view.findViewById(R.id.destination);
        destination.setText(ride.getLocations().get(0).getDestination().getAddress());

        // set vehicle type
        TextView vehicleType = (TextView) view.findViewById(R.id.vehicle_type);
        vehicleType.setText(ride.getVehicleType());

        // set baby and pet transport
        TextView petTransport = (TextView) view.findViewById(R.id.pet_transport);
        petTransport.setText(Boolean.toString(ride.isPetTransport()));
        TextView babyTransport = (TextView) view.findViewById(R.id.baby_transport);
        babyTransport.setText(Boolean.toString(ride.isBabyTransport()));

        // set time
        time = (TextView) view.findViewById(R.id.time_text);
        ImageButton timepicker = view.findViewById(R.id.timepicker);
        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                time.setText("Order time: " + hourOfDay + ":" + minute);
                                hourOrder = hourOfDay;
                                minuteOrder = minute;
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        cancelOrdering(view);
        orderRide(view);

        return view;
    }

    private boolean isReservation(){
        Date current = Calendar.getInstance().getTime();
        orderDate = new Date();
        if(time.getText().toString().equals("Order time: now")) return false;

        int returnVal = LocalTime.of(current.getHours(), current.getMinutes()).compareTo(LocalTime.of(hourOrder, minuteOrder));
        if (returnVal > 0) {
            // the entered time belongs to the next day
            orderDate = new Date(orderDate.getTime()+24*60*60*1000);
        }
        orderDate.setHours(hourOrder);
        orderDate.setMinutes(minuteOrder);

        return orderDate.getTime() - current.getTime() > 20 * 60 * 1000;
    }

    private void orderRide(View view) {
        Button order = (Button) view.findViewById(R.id.order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<User> passengers = new ArrayList<>();
                passengers.add(getCurrentUser());
                isReservation();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                String scheduledTime = format.format(orderDate);

                Ride orderRide = new Ride(scheduledTime, ride.isBabyTransport(), ride.isPetTransport(),
                        ride.getLocations(), ride.getVehicleType(), passengers);

                Ride response =  RideService.insertRide(orderRide);

                Toast toast;
                if(response == null)
                    toast = Toast.makeText(view.getContext(), "Cannot create a ride while you have one already pending!", Toast.LENGTH_LONG);
                else
                    toast = Toast.makeText(view.getContext(), "Your order has been sent! \nPlease wait... system is looking for the driver.", Toast.LENGTH_LONG);
                toast.show();

                dismiss();
            }
        });
    }

    private void cancelOrdering(View view) {
        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private User getCurrentUser() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return AuthService.getCurrentUser();
    }
}