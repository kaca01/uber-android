package com.example.ubermobileapp.fragments.home;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.home.PassengerMainActivity;


public class CreateRide1Fragment extends Fragment {

    EditText departure;
    EditText destination;

    public CreateRide1Fragment() {}


    public static CreateRide1Fragment newInstance(String param1, String param2) { return new CreateRide1Fragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_ride1, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button confirm = view.findViewById(R.id.confirm);
        departure = view.findViewById(R.id.departure);
        destination = view.findViewById(R.id.destination);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (departure.getText().toString().isEmpty() || destination.getText().toString().isEmpty()) {
                    Toast toast = Toast.makeText(view.getContext(), "You must fill all the fields!", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                boolean flag1 = MapFragment.searchLocation(PassengerMainActivity.geocoder, departure.getText().toString(), true);
                boolean flag2 = MapFragment.searchLocation(PassengerMainActivity.geocoder, destination.getText().toString(), false);
                if (!flag1 || !flag2) {
                    Toast toast = Toast.makeText(view.getContext(), "Address not found!", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                PassengerMainActivity.order.setDeparture(departure.getText().toString());
                PassengerMainActivity.order.setDestination(destination.getText().toString());
                ((PassengerMainActivity)getActivity()).changeToSecondFragment();
            }
        });

        setParams();
    }

    private void setParams(){
        String dep = PassengerMainActivity.order.getDeparture();
        departure.setText(dep);
        String des = PassengerMainActivity.order.getDestination();
        destination.setText(des);
    }
}