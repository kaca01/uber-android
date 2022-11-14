package com.example.ubermobileapp.fragments.rideHistory;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ubermobileapp.DriverActivities.RideInformationActivity;
import com.example.ubermobileapp.R;
import com.example.ubermobileapp.adapters.RideAdapter;
import com.example.ubermobileapp.model.Ride;
import com.example.ubermobileapp.tools.Mockup;


public class AllRidesFragment extends ListFragment {

    public AllRidesFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add adapter
        RideAdapter adapter = new RideAdapter(getActivity());
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_all_rides, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Ride ride = Mockup.getRides().get(position);

        Intent intent = new Intent(getActivity(), RideInformationActivity.class);

        intent.putExtra("date", ride.getDate());
        intent.putExtra("start_time", ride.getStartTime());
        intent.putExtra("end_time", ride.getEndTime());
        intent.putExtra("distance", Double.toString(ride.getDistance()));
        intent.putExtra("cost", Double.toString(ride.getCost()));
        intent.putExtra("path", ride.getPath());

        startActivity(intent);
    }
}