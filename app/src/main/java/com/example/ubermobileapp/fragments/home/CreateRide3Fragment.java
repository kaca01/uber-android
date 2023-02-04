package com.example.ubermobileapp.fragments.home;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.home.PassengerMainActivity;
import com.example.ubermobileapp.models.pojo.ride.FavoriteOrder;
import com.example.ubermobileapp.services.implementation.RideService;
import com.example.ubermobileapp.services.utils.AuthService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class CreateRide3Fragment extends Fragment {
    String favoriteName;
    CheckBox baby;
    CheckBox pet;
    TextView textView;
    int hourOrder;
    int minuteOrder;
    Date orderDate;

    public CreateRide3Fragment() {}

    public static CreateRide3Fragment newInstance(String param1, String param2) { return new CreateRide3Fragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_ride3, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        baby = view.findViewById(R.id.baby_transport);
        pet = view.findViewById(R.id.pet_transport);
        textView = view.findViewById(R.id.time_text);
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
                                textView.setText("Order time: " + hourOfDay + ":" + minute);
                                hourOrder = hourOfDay;
                                minuteOrder = minute;
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        Button confirm = view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOrderData();
                checkIfReservation();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                PassengerMainActivity.order.setScheduledTime(format.format(orderDate));
                if (orderDate.getTime() - Calendar.getInstance().getTime().getTime() > 5*60*60*1000) {
                    Toast.makeText(view.getContext(), "Reservation can be made within next 5 hours only!", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast toast = Toast.makeText(view.getContext(), "Your order has been sent! \nPlease wait... system is looking for the driver.", Toast.LENGTH_LONG);
                toast.show();
                getView().setVisibility(View.GONE);
                ((PassengerMainActivity)getActivity()).setCancelButtonVisible();
                ((PassengerMainActivity)getActivity()).setBackButtonInvisible();

                PassengerMainActivity.insertRide();
            }
        });

        favoriteDialog(view);
    }

    private void setOrderData(){
        PassengerMainActivity.order.setBabyTransport(baby.isChecked());
        PassengerMainActivity.order.setPetTransport(pet.isChecked());
        PassengerMainActivity.order.setFavoriteName(favoriteName);
    }

    private void checkIfReservation(){
        Date current = Calendar.getInstance().getTime();
        orderDate = new Date();
        if(textView.getText().toString().equals("Order time: now")) return;

        int returnVal = LocalTime.of(current.getHours(), current.getMinutes()).compareTo(LocalTime.of(hourOrder, minuteOrder));
        if (returnVal > 0) {
            //uneseno vrijeme pripada iducem danu
            orderDate = new Date(orderDate.getTime()+24*60*60*1000);
        }
        orderDate.setHours(hourOrder);
        orderDate.setMinutes(minuteOrder);
    }

    private void favoriteDialog(View view){
        TextView addFavorite = view.findViewById(R.id.favorites_button);

        addFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Add favorite ride");
                final EditText input = new EditText(view.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setHint("Add name for your favorite ride...");
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        favoriteName = input.getText().toString();
                        if (favoriteName.equals("")) Toast.makeText(view.getContext(),
                                "Error: You must type a name!", Toast.LENGTH_SHORT).show();
                        else {
                            setOrderData();
                            FavoriteOrder favoriteOrder = new FavoriteOrder(PassengerMainActivity.order, AuthService.getCurrentUser());
                            RideService.insertFavoriteLocation(favoriteOrder);
                            Toast.makeText(view.getContext(), "Successfully added!", Toast.LENGTH_SHORT).show();
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
}