package com.example.ubermobileapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ubermobileapp.DriverActivities.RideInformationActivity;
import com.example.ubermobileapp.adapters.FavoriteRideAdapter;
import com.example.ubermobileapp.model.Ride;
import com.example.ubermobileapp.tools.Mockup;


public class AllFavRidesFragment extends ListFragment {

    public AllFavRidesFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add adapter
        FavoriteRideAdapter adapter = new FavoriteRideAdapter(getActivity());
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_all_rides, container, false);
    }

}