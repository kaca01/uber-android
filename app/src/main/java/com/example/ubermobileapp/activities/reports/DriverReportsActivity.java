package com.example.ubermobileapp.activities.reports;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.account.DriverAccountActivity;
import com.example.ubermobileapp.activities.history.DriverRideHistoryActivity;
import com.example.ubermobileapp.activities.home.DriverMainActivity;
import com.example.ubermobileapp.activities.inbox.DriverInboxActivity;
import com.example.ubermobileapp.models.Report;
import com.example.ubermobileapp.tools.Mockup;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class DriverReportsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.ubermobileapp.R.layout.activity_reports);
        setNavigation();
        setReportData(1, 2);
        setChart(findViewById(R.id.lineChart), "Number of Rides Report");
        setChart(findViewById(R.id.lineChart2), "Crossed km Report");
        
        LineChart chart = (findViewById(R.id.lineChart3));
        chart.setVisibility(View.GONE);
        TextView moneySpent = (findViewById(R.id.spentMoney));
        moneySpent.setVisibility(View.GONE);
        TextView averageSpent = (findViewById(R.id.averageSpent));
        averageSpent.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(DriverReportsActivity.this,
                DriverAccountActivity.class));
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
                        startActivity(new Intent(DriverReportsActivity.this, DriverMainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.page_history:
                        startActivity(new Intent(DriverReportsActivity.this, DriverRideHistoryActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.page_inbox:
                        startActivity(new Intent(DriverReportsActivity.this, DriverInboxActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.page_account:
                        return true;
                }
                return false;
            }
        });
    }

    private void setReportData(int firstReportID, int secondReportID) {
        Report firstReport = Mockup.getReport(firstReportID);
        Report secondReport = Mockup.getReport(secondReportID);

        TextView numOfRides = findViewById(R.id.numOfRides);
        TextView averageRides = findViewById(R.id.averageRides);
        TextView crossed = findViewById(R.id.crossedkm);
        TextView crossedAverage = findViewById(R.id.averageCrossed);

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
    }
}
