package com.example.ubermobileapp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.models.pojo.ride.FavoriteOrder;
import com.example.ubermobileapp.services.implementation.RideService;

import java.util.List;

public class FavoriteRideAdapter extends BaseAdapter {

    private Activity activity;
    private List<FavoriteOrder> favoriteOrders = RideService.getFavorites();;

    public FavoriteRideAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return favoriteOrders.size();
    }

    @Override
    public Object getItem(int position) {
        return favoriteOrders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(convertView==null)
            view = activity.getLayoutInflater().inflate(
                    R.layout.fragment_passenger_ride_favorite_list, null);

        FavoriteOrder ride = favoriteOrders.get(position);

        TextView departure = (TextView) view.findViewById(R.id.departure);
        TextView destination = (TextView) view.findViewById(R.id.destination);
        TextView name = (TextView) view.findViewById(R.id.name);

        departure.setText(ride.getLocations().get(0).getDeparture().getAddress());
        destination.setText(ride.getLocations().get(0).getDestination().getAddress());
        name.setText(ride.getFavoriteName());

        return view;
    }
}
