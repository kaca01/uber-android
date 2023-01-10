package com.example.ubermobileapp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.fragments.review.LeavingReviewFragment;
import com.example.ubermobileapp.model.Ride;
import com.example.ubermobileapp.tools.Mockup;

public class PassengerRideAdapter extends BaseAdapter {

    private Activity activity;

    public PassengerRideAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return Mockup.getRides().size();
    }

    @Override
    public Object getItem(int position) {
        return Mockup.getRides().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Ride ride = Mockup.getRides().get(position);

        if(convertView==null)
            view = activity.getLayoutInflater().inflate(R.layout.start_ride_info_passenger, null);

       changeFavButton(view);

        TextView startTime = (TextView) view.findViewById(R.id.start_time);
        TextView endTime = (TextView) view.findViewById(R.id.end_time);
        TextView date = (TextView) view.findViewById(R.id.date);
        TextView cost = (TextView) view.findViewById(R.id.cost);
        TextView passengerNum = (TextView) view.findViewById(R.id.passenger_num);
        TextView distance = (TextView) view.findViewById(R.id.distance);
        ImageView path = (ImageView) view.findViewById(R.id.ride);

        startTime.setText(ride.getStartTime());
        endTime.setText(ride.getEndTime());
        date.setText(ride.getDate());
        cost.setText(Double.toString(ride.getCost()));
        passengerNum.setText(Integer.toString(ride.getPassengerNum()));
        distance.setText(Double.toString(ride.getDistance()));
        path.setImageResource(ride.getPath());

        return view;
    }

    private void changeFavButton(View view) {
        Button favorite = view.findViewById(R.id.add_to_favorite);
        favorite.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(favorite.getText().toString().contains("NOT"))
                    favorite.setText("FAVORITE");
                else
                    favorite.setText("NOT A FAVORITE");
            }
        });
    }
}
