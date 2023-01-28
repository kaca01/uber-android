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
    Pie pie;
    List<DataEntry> dataEntries = new ArrayList<>();

    List<String> days = new ArrayList<>();

    // rides statistic
    HashMap<String, Integer> numberOfRidesPerDay = new HashMap<>();
    private int numberOfRides;

    // cost statistic
    HashMap<String, Double> totalCostPerDay = new HashMap<>();
    private double totalCost;

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
        pie = AnyChart.pie();
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
        displayRidesStatistic();
        addSpinnerActionListener(view);

        return view;
    }

    private void displayRidesStatistic() {
        dataEntries.clear();
        for (String key: numberOfRidesPerDay.keySet()) {
            dataEntries.add(new ValueDataEntry(key, numberOfRidesPerDay.get(key)));
        }

        pie.data(dataEntries);
        pie.title("Number of rides");
        anyChart.clear();
        anyChart.setChart(pie);
//        anyChart.refreshDrawableState();
    }

    private void displayCostStatistic() {
        dataEntries.clear();
        for (String key: totalCostPerDay.keySet()) {
            dataEntries.add(new ValueDataEntry(key, totalCostPerDay.get(key)));
        }

        pie.data(dataEntries);
        pie.title("Total cost");
        anyChart.clear();
        anyChart.setChart(pie);
//        anyChart.refreshDrawableState();
    }

    @SuppressLint("SetTextI18n")
    private void addSpinnerActionListener(View view) {
        TextView total = view.findViewById(R.id.total);
        TextView average = view.findViewById(R.id.average);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0)
                {
                    total.setText(Integer.toString(numberOfRides));
                    average.setText(String.format("%.02f", (float)numberOfRides/30));
                    displayRidesStatistic();
                } else if (position==1)
                {
                    total.setText(String.format("%.02f", totalCost));
                    average.setText(String.format("%.02f", totalCost/30));
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

    private List<Ride> getRides() {
        long id = AuthService.getCurrentUser().getId();
        return PassengerService.getRides(id);
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
        List<Ride> rides = getRides();
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

    private void populateTotalCost() throws ParseException {
        List<Ride> rides = getRides();
        Date oneMonthAgo = getOneMonthAgoDate();

        for (Ride ride: rides) {
            if(!ride.getStatus().equals("FINISHED")) continue;

            @SuppressLint("SimpleDateFormat")
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(ride.getStartTime().split("T")[0]);
            assert startDate != null;

            if (startDate.after(oneMonthAgo)) {
                totalCost += ride.getTotalCost();
                String key = ride.getStartTime().split("T")[0];
                if (totalCostPerDay.containsKey(key))
                    totalCostPerDay.put(key, numberOfRidesPerDay.get(key) + ride.getTotalCost());
                else totalCostPerDay.put(key, ride.getTotalCost());
            }
        }
    }
}