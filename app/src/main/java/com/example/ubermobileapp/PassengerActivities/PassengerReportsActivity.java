package com.example.ubermobileapp.PassengerActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ubermobileapp.R;
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

        LineDataSet lineDataSet1 = new LineDataSet(yAXEScos, "cos");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.BLUE);

        LineDataSet lineDataSet2 = new LineDataSet(yAXESsin, "sin");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.RED);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        lc.setData(new LineData(xaxes, lineDataSets));
        lc.setVisibleXRangeMaximum(65f);
        lc.setDescription(description);
        lc.setDescriptionTypeface(Typeface.DEFAULT_BOLD);
        lc.setDescriptionTextSize(15);
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
}