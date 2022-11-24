package com.example.ubermobileapp.fragments.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ubermobileapp.R;

public class StatisticsFragment extends Fragment{
    Spinner spin;

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

        spin = (Spinner)view.findViewById(R.id.range_spinner);
        String[] options = { "Today", "This week", "This month"};
        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, options);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0) //today
                {
                    declined.setText("5");
                    accepted.setText("25");
                    hours.setText("4");
                    earnings.setText("12500");
                } else if (position==1)  //week
                {
                    declined.setText("25");
                    accepted.setText("63");
                    hours.setText("48");
                    earnings.setText("65000");
                }
                else if(position==2){  //month
                    declined.setText("48");
                    accepted.setText("101");
                    hours.setText("192");
                    earnings.setText("120000");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        return view;
    }
}