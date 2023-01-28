package com.example.ubermobileapp.fragments.account;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.ubermobileapp.R;
import com.example.ubermobileapp.models.pojo.ride.Ride;
import com.example.ubermobileapp.services.implementation.PassengerService;
import com.example.ubermobileapp.services.utils.AuthService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PassengerStatisticsFragment extends Fragment {
    Spinner spinner;

    AnyChartView anyChart;

    List<String> days = new ArrayList<>();

    // rides statistic
    HashMap<String, Integer> numberOfRidesPerDay = new HashMap<>();
    private int numberOfRides;

    public PassengerStatisticsFragment() {
        // Required empty public constructor
    }

    public static PassengerStatisticsFragment newInstance(String param1, String param2) {
        PassengerStatisticsFragment fragment = new PassengerStatisticsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passenger_statistics, container, false);
        anyChart = view.findViewById(R.id.pieChart);
        spinner = (Spinner)view.findViewById(R.id.range_spinner);
        String[] options = { "Rides", "Cost", "kms"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, options);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        populateData();
        try {
            populateNumberOfRides();
            populateTotalCost();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addSpinnerActionListener(view);

        return view;
    }

    private void displayRidesStatistic() {
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
        for (String key: numberOfRidesPerDay.keySet()) {
            dataEntries.add(new ValueDataEntry(key, numberOfRidesPerDay.get(key)));
        }

        pie.data(dataEntries);
        pie.title("Number of rides");
        anyChart.setChart(pie);
    }

    private void displayCostStatistic() {

    }

    private void addSpinnerActionListener(View view) {
        TextView total = view.findViewById(R.id.total);
        TextView average = view.findViewById(R.id.average);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0)
                {
                    total.setText(Integer.toString(numberOfRides));
                    average.setText(String.format("%.02f", (float)numberOfRides/30));
                    displayRidesStatistic();
                } else if (position==1)
                {
                    total.setText("20000");
                    average.setText("420");
                    displayCostStatistic();
                }
                else if(position==2){
                    total.setText("25");
                    average.setText("2.1");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    private Date getOneMonthAgoDate() {
        return new Date(new Date().getTime() - (1000L * 60 * 60 * 24 * 30));
    }

    private void populateData() {
        Date date = getOneMonthAgoDate();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < 30; i++) {
            date = new Date(date.getTime() + (1000L * 60 * 60 * 24));
            days.add(format.format(date));
        }
    }

    private void populateNumberOfRides() throws ParseException {
        long id = AuthService.getCurrentUser().getId();
        List<Ride> rides = PassengerService.getRides(id);
        Date oneMonthAgo = getOneMonthAgoDate();

        for (Ride ride: rides) {
            if (!ride.getStatus().equals("FINISHED")) continue;

            @SuppressLint("SimpleDateFormat")
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(ride.getStartTime().split("T")[0]);
            assert startDate != null;

            if (startDate.after(oneMonthAgo)) {
                numberOfRides++;
                String key = ride.getStartTime().split("T")[0];
                if (numberOfRidesPerDay.containsKey(key))
                    numberOfRidesPerDay.put(key, numberOfRidesPerDay.get(key) + 1);
                else numberOfRidesPerDay.put(key, 1);
            }
        }
    }

    private void populateTotalCost() {

    }
}