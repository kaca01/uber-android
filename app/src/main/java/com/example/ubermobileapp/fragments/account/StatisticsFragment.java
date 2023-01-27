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
import com.example.ubermobileapp.models.enumeration.RideStatus;
import com.example.ubermobileapp.models.pojo.ride.Ride;
import com.example.ubermobileapp.services.implementation.DriverService;
import com.example.ubermobileapp.services.utils.AuthService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StatisticsFragment extends Fragment{
    Spinner spin;
    TextView declined;
    TextView accepted;
    TextView hours;
    TextView earnings;
    List<Double> data = Arrays.asList(0d, 0d, 0d, 0d);

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

        declined = (TextView)view.findViewById(R.id.declined_drives);
        accepted = (TextView)view.findViewById(R.id.accepted_drives);
        hours = (TextView)view.findViewById(R.id.hours);
        earnings = (TextView)view.findViewById(R.id.earnings);

        setData();

        spin = (Spinner)view.findViewById(R.id.range_spinner);
        String[] options = { "1 day", "7 days", "30 days"};
        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, options);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0) //today
                {
                    try {
                        getStatisticsData(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (position==1)  //week
                {
                    try {
                        getStatisticsData(7);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if(position==2){  //month
                    try {
                        getStatisticsData(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                try {
                    getStatisticsData(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    private void getStatisticsData(int days) throws InterruptedException {
        data = Arrays.asList(0d, 0d, 0d, 0d);
        Thread thread = new Thread(){
            public void run(){
                List<Ride> rides = DriverService.getRides(AuthService.getCurrentUser().getId());
                Date minDate = new Date();
                minDate = new Date(minDate.getTime()- (long) days *24*60*60*1000);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                for (Ride r: rides){
                    try {
                        if (format.parse(r.getScheduledTime()).after(minDate)){
                            if(r.getStatus().equals(RideStatus.REJECTED.toString())){
                                data.set(0, data.get(0)+1);
                            }
                            else if(r.getStatus().equals(RideStatus.ACCEPTED.toString())){
                                data.set(1, data.get(1)+1);
                            }
                            data.set(2, data.get(2)+r.getEstimatedTimeInMinutes());
                            data.set(3, data.get(3)+r.getTotalCost());
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        thread.start();
        thread.join();
        setData();
    }

    private void setData(){
        declined.setText(String.valueOf(data.get(0).intValue()));
        accepted.setText(String.valueOf(data.get(1).intValue()));
        earnings.setText(String.valueOf(data.get(3).intValue()));
        hours.setText(String.valueOf(Math.round(data.get(2)/60)));
    }
}