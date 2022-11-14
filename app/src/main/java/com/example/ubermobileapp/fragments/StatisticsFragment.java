package com.example.ubermobileapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.UserLoginActivity;

import org.w3c.dom.Text;

public class StatisticsFragment extends Fragment {



    public StatisticsFragment() {
        // Required empty public constructor
    }

    public static StatisticsFragment newInstance() {
        return  new StatisticsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        TextView declined = (TextView)view.findViewById(R.id.declined_drives);
        TextView accepted = (TextView)view.findViewById(R.id.accepted_drives);
        TextView hours = (TextView)view.findViewById(R.id.hours);
        TextView earnings = (TextView)view.findViewById(R.id.earnings);

        Button day = view.findViewById(R.id.button_day);
        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                declined.setText("5");
                accepted.setText("25");
                hours.setText("4");
                earnings.setText("12500");
            }
        });

        Button week = view.findViewById(R.id.button_week);
        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                declined.setText("25");
                accepted.setText("63");
                hours.setText("48");
                earnings.setText("65000");
            }
        });

        Button month = view.findViewById(R.id.button_month);
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                declined.setText("48");
                accepted.setText("101");
                hours.setText("192");
                earnings.setText("120000");
            }
        });

        return view;
    }
}