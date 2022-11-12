package com.example.ubermobileapp.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ubermobileapp.R;

public class PassengerAccountOptionsFragment extends Fragment {
    AlertDialog alertDialog;

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

        Button creditCard = view.findViewById(R.id.creditCardBtn);
        creditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(requireActivity());

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View newView = inflater.inflate(R.layout.fragment_credit_card, null);

                dialog.setView(newView)
                        .setTitle("Credit Card")
                        .setCancelable(false)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                alertDialog = dialog.create();
                alertDialog.show();
            }
        });

        return view;
    }
}