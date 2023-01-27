package com.example.ubermobileapp.activities.reports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.account.PassengerAccountActivity;
import com.example.ubermobileapp.activities.history.PassengerRideHistoryActivity;
import com.example.ubermobileapp.activities.home.PassengerMainActivity;
import com.example.ubermobileapp.activities.inbox.PassengerInboxActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class PassengerReportsActivity extends AppCompatActivity {
    AnyChartView anyChart;

    String[] months = {"January", "February", "March"};
    int[] values = {120, 150, 80};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.ubermobileapp.R.layout.activity_passenger_reports);
        setNavigation();

        anyChart = findViewById(R.id.pieChart);

        setUpPieChart();

    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(PassengerReportsActivity.this,
                PassengerAccountActivity.class));
        overridePendingTransition(0,0);
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