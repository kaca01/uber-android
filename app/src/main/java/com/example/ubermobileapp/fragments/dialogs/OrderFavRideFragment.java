package com.example.ubermobileapp.fragments.dialogs;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.models.pojo.ride.RideOrder;
import com.example.ubermobileapp.models.pojo.ride.FavoriteOrder;
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


public class OrderFavRideFragment extends DialogFragment {

    FavoriteOrder ride;
    RideOrder order = new RideOrder();
    int hourOrder;
    int minuteOrder;
    Date orderDate;
    TextView time;

    public OrderFavRideFragment(FavoriteOrder ride) {
        this.ride = ride;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_ride, container, false);

        // set locations
        TextView departure = (TextView) view.findViewById(R.id.departure);
        departure.setText(ride.getLocations().get(0).getDeparture().getAddress());
        TextView destination = (TextView) view.findViewById(R.id.destination);
        destination.setText(ride.getLocations().get(0).getDestination().getAddress());

        // set vehicle type
        Spinner spin = (Spinner)view.findViewById(R.id.vehicle_type_spinner);
        String[] options = { "STANDARD", "LUXURY", "VAN"};
        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, options);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        for (int i = 0; i < options.length; i++) {
            if(ride.getVehicleType().equals(options[i]))
                spin.setSelection(i);
        }
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0) { }
                else if (position==1) { }
                else if (position==2) { }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // set baby and pet transport
        CheckBox baby = (CheckBox) view.findViewById(R.id.baby_transport);
        baby.setChecked(ride.isBabyTransport());
        CheckBox pet = (CheckBox) view.findViewById(R.id.pet_transport);
        pet.setChecked(ride.isPetTransport());

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


        Button confirm = view.findViewById(R.id.order);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order.setDeparture(ride.getLocations().get(0).getDeparture().getAddress());
                order.setDestination(ride.getLocations().get(0).getDestination().getAddress());
                order.setVehicleType(spin.getSelectedItemPosition());
                order.setBabyTransport(baby.isChecked());
                order.setPetTransport(pet.isChecked());
                isReservation();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                order.setScheduledTime(format.format(orderDate));

                ArrayList<String> passengers = new ArrayList<>();
                passengers.add(getCurrentUser().getEmail());
                order.setEmails(passengers);

                // order ride
                Ride orderRide = new Ride(order);
                Ride response = RideService.insertRide(orderRide);

                Toast toast;
                if(response == null)
                    toast = Toast.makeText(view.getContext(), "Cannot create a ride while you have one already pending!", Toast.LENGTH_LONG);
                else
                    toast = Toast.makeText(view.getContext(), "Your order has been sent! \nPlease wait... system is looking for the driver.", Toast.LENGTH_LONG);
                toast.show();

                dismiss();
            }
        });

        Button cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

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

    private User getCurrentUser() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return AuthService.getCurrentUser();
    }
}
