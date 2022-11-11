package com.example.ubermobileapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return inflater.inflate(R.layout.fragment_passenger_account_options, container,
                false);
    }
}