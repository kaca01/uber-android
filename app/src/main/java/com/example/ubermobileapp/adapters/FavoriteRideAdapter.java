package com.example.ubermobileapp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.models.Ride;
import com.example.ubermobileapp.tools.Mockup;

public class FavoriteRideAdapter extends BaseAdapter {
    private Activity activity;

    public FavoriteRideAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return Mockup.getFavoriteRides().size();
    }

    @Override
    public Object getItem(int position) {
        return Mockup.getFavoriteRides().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Ride ride = Mockup.getFavoriteRides().get(position);

        if(convertView==null)
            view = activity.getLayoutInflater().inflate(
                    R.layout.fragment_passenger_ride_favorite_list, null);

        TextView startTime = (TextView) view.findViewById(R.id.start_time);
        TextView endTime = (TextView) view.findViewById(R.id.end_time);
        TextView date = (TextView) view.findViewById(R.id.date);
        TextView cost = (TextView) view.findViewById(R.id.cost);
        TextView passengerNum = (TextView) view.findViewById(R.id.passenger_num);
        TextView distance = (TextView) view.findViewById(R.id.distance);

        startTime.setText(ride.getStartTime());
        endTime.setText(ride.getEndTime());
        date.setText(ride.getDate());
        cost.setText(Double.toString(ride.getCost()));
        passengerNum.setText(Integer.toString(ride.getPassengers().size()));
        distance.setText(Double.toString(ride.getDistance()));

        return view;
    }
}
