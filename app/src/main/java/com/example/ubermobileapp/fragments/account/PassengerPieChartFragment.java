package com.example.ubermobileapp.fragments.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.ubermobileapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PassengerPieChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PassengerPieChartFragment extends Fragment {

    AnyChartView anyChart;

    String[] months = {"January", "February", "March"};
    int[] values = {120, 150, 80};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PassengerPieChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PassengerPieChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PassengerPieChartFragment newInstance(String param1, String param2) {
        PassengerPieChartFragment fragment = new PassengerPieChartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_passenger_pie_chart, container, false);

        anyChart = view.findViewById(R.id.pieChart);
        
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