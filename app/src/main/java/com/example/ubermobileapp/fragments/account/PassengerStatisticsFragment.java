package com.example.ubermobileapp.fragments.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.ubermobileapp.R;

import java.util.ArrayList;
import java.util.List;

public class PassengerStatisticsFragment extends Fragment {
    Spinner spinner;

    AnyChartView anyChart;

    String[] months = {"January", "February", "March"};
    int[] values = {120, 150, 80};

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

        setUpPieChart();

        return view;
    }

    private void setUpPieChart() {
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
        for (int i = 0; i < months.length; i++) {
            dataEntries.add(new ValueDataEntry(months[i], values[i]));
        }

        pie.data(dataEntries);
        pie.title("Reports");
        anyChart.setChart(pie);
    }
}