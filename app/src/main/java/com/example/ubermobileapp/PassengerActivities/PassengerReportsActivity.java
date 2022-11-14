package com.example.ubermobileapp.PassengerActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.model.Report;
import com.example.ubermobileapp.tools.Mockup;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class PassengerReportsActivity extends AppCompatActivity {
    LineChart lineChart;
    LineChart lineChart2;
    LineChart lineChart3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.ubermobileapp.R.layout.activity_passenger_reports);
        setNavigation();
        setReportData(1, 2, 3);
        setChart(findViewById(R.id.lineChart), "Number of Rides Report");
        setChart(findViewById(R.id.lineChart2), "Crossed km Report");
        setChart(findViewById(R.id.lineChart3), "Money Spent Report");
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(PassengerReportsActivity.this,
                PassengerAccountActivity.class));
        overridePendingTransition(0,0);
    }

    private void setChart(LineChart lc, String description) {
        ArrayList<String> xAXES = new ArrayList<>();
        ArrayList<Entry> yAXESsin = new ArrayList<Entry>();
        ArrayList<Entry> yAXEScos = new ArrayList<Entry>();
        double x = 0;
        int numDataPoints = 1000;
        for (int i = 0; i < numDataPoints; i++) {
            float sinFuction = Float.parseFloat(String.valueOf(Math.sin(x)));
            float cosFuction = Float.parseFloat(String.valueOf(Math.cos(x)));
            x = x + 0.1;
            yAXESsin.add(new Entry(sinFuction, i));
            yAXEScos.add(new Entry(cosFuction, i));
            xAXES.add(i, String.valueOf(x));
        }
        String[] xaxes = new String[xAXES.size()];
        for(int i = 0; i < xAXES.size(); i++) {
            xaxes[i] = xAXES.get(i).toString();
        }

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(yAXEScos, "");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.BLUE);

        LineDataSet lineDataSet2 = new LineDataSet(yAXESsin, "");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.RED);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        lc.setData(new LineData(xaxes, lineDataSets));
        lc.setVisibleXRangeMaximum(65f);
        lc.setDescription(description);
        lc.setDescriptionTypeface(Typeface.DEFAULT_BOLD);
        lc.setDescriptionTextSize(15);
        lc.setDescriptionColor(Color.parseColor("#96D49C"));
    }

    private void setNavigation() {
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setSelectedItemId(R.id.page_account);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_map:
                        startActivity(new Intent(PassengerReportsActivity.this,
                                PassengerMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_history:
                        startActivity(new Intent(PassengerReportsActivity.this,
                                PassengerRideHistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_inbox:
                        startActivity(new Intent(PassengerReportsActivity.this,
                                PassengerInboxActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_account:
                        return true;
                }
                return false;
            }
        });
    }

    private void setReportData(int firstReportID, int secondReportID, int thirdReportID) {
        Report firstReport = Mockup.getReport(firstReportID);
        Report secondReport = Mockup.getReport(secondReportID);
        Report thirdReport = Mockup.getReport(thirdReportID);

        TextView numOfRides = findViewById(R.id.numOfRides);
        TextView averageRides = findViewById(R.id.averageRides);
        TextView crossed = findViewById(R.id.crossedkm);
        TextView crossedAverage = findViewById(R.id.averageCrossed);
        TextView moneySpent = findViewById(R.id.spentMoney);
        TextView averageSpent = findViewById(R.id.averageSpent);

        String textTotal = "Total: ";
        String textAverage = "Average: ";
        String total, average;

        total = textTotal + Double.toString(firstReport.getTotal());
        numOfRides.setText(total);
        average = textAverage + Double.toString(firstReport.getAverage());
        averageRides.setText(average);

        total = textTotal + Double.toString(secondReport.getTotal());
        crossed.setText(total);
        average = textAverage + Double.toString(secondReport.getAverage());
        crossedAverage.setText(average);

        total = textTotal + Double.toString(thirdReport.getTotal());
        moneySpent.setText(total);
        average = textAverage + Double.toString(thirdReport.getAverage());
        averageSpent.setText(average);
    }
}