package com.example.ubermobileapp.fragments.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.history.DriverRideHistoryActivity;
import com.example.ubermobileapp.activities.history.RideInformationActivity;
import com.example.ubermobileapp.activities.home.PassengerMainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateRide1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateRide1Fragment extends Fragment {


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
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PassengerMainActivity)getActivity()).changeToSecondFragment();
            }
        });
    }
}