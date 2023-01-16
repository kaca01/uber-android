package com.example.ubermobileapp.fragments.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ubermobileapp.activities.reports.PassengerReportsActivity;
import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.startup.UserLoginActivity;
import com.example.ubermobileapp.services.utils.AuthService;
import com.example.ubermobileapp.tools.Timer;

public class DriverAccountOptionsFragment extends Fragment {
    AlertDialog startDateDialog;
    AlertDialog endDateDialog;

    public DriverAccountOptionsFragment() {
        // Required empty public constructor
    }

    public static DriverAccountOptionsFragment newInstance() {
        return new DriverAccountOptionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_driver_account_options, container,
                false);

        CardView reports = view.findViewById(R.id.thirdCard);
        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //createStartDateDialog();
            }
        });

        CardView logOut = view.findViewById(R.id.fourthCard);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthService.logout();
                Timer.setInstance();
                startActivity(new Intent(getActivity(), UserLoginActivity.class));
            }
        });

        return view;
    }

    protected void createStartDateDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View newView = inflater.inflate(R.layout.fragment_start_date, null);

        dialog.setView(newView)
                .setTitle("Reports")
                .setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        createEndDateDialog();
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        startDateDialog = dialog.create();
        startDateDialog.show();
    }

    protected void createEndDateDialog() {
        startDateDialog.cancel();
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View newView = inflater.inflate(R.layout.fragment_end_date, null);

        dialog.setView(newView)
                .setTitle("Reports")
                .setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(getActivity(), PassengerReportsActivity.class));
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        endDateDialog = dialog.create();
        endDateDialog.show();
    }
}