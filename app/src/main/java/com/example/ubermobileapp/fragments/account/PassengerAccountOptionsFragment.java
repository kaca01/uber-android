package com.example.ubermobileapp.fragments.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ubermobileapp.activities.favorite_ride.PassengerFavoriteRoutesActivity;
import com.example.ubermobileapp.R;

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

        return view;
    }
}