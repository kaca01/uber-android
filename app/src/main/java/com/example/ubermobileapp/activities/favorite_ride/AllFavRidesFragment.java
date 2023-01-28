package com.example.ubermobileapp.activities.favorite_ride;

import android.os.Bundle;

import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.adapters.FavoriteRideAdapter;


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
        return inflater.inflate(R.layout.all_fav_rides, container, false);
    }

}