package com.example.ubermobileapp.fragments.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ubermobileapp.activities.account.DriverAccountActivity;
import com.example.ubermobileapp.activities.favorite_ride.PassengerFavoriteRoutesActivity;
import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.startup.UserLoginActivity;
import com.example.ubermobileapp.androidService.AcceptingRideService;
import com.example.ubermobileapp.models.passenger.CreditCard;
import com.example.ubermobileapp.services.utils.AuthService;
import com.example.ubermobileapp.tools.Mockup;
import com.example.ubermobileapp.tools.Timer;

public class PassengerAccountOptionsFragment extends Fragment {

    public PassengerAccountOptionsFragment() {
        // Required empty public constructor
    }

    public static PassengerAccountOptionsFragment newInstance() {
        return new PassengerAccountOptionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_passenger_account_options, container,
                false);

        CardView favoriteLocations = view.findViewById(R.id.firstCard);
        favoriteLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PassengerFavoriteRoutesActivity.class));
            }
        });

        TextView logOut = view.findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthService.logout();
                Timer.setInstance();
                getActivity().stopService(new Intent(getActivity(), AcceptingRideService.class));
                startActivity(new Intent(getActivity(), UserLoginActivity.class));
            }
        });

        return view;
    }
}