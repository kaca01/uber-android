package com.example.ubermobileapp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.models.pojo.ride.Ride;
import com.example.ubermobileapp.models.pojo.user.User;
import com.example.ubermobileapp.services.implementation.PassengerService;
import com.example.ubermobileapp.services.utils.AuthService;

import java.util.List;

public class PassengerRideAdapter extends BaseAdapter {

    private Activity activity;
    private List<Ride> rides = setRides();

    public PassengerRideAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return rides.size();
    }

    @Override
    public Object getItem(int position) {
        return rides.get(position);
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
            view = activity.getLayoutInflater().inflate(R.layout.start_ride_info_passenger, null);

        changeFavButton(view);

        Ride ride = rides.get(position);

        String[] dateAndStartTime = ride.getStartTime().split("T");
        String[] dateAndEndTime = ride.getEndTime().split("T");
        String[] startTime = dateAndStartTime[1].split(":");
        String[] endTime = dateAndEndTime[1].split(":");

        TextView departure = (TextView) view.findViewById(R.id.departure);
        TextView destination = (TextView) view.findViewById(R.id.destination);
        TextView startTimeText = (TextView) view.findViewById(R.id.start_time);
        TextView endTimeText = (TextView) view.findViewById(R.id.end_time);
        TextView date = (TextView) view.findViewById(R.id.date);
        TextView cost = (TextView) view.findViewById(R.id.cost);
        TextView passengerNum = (TextView) view.findViewById(R.id.passenger_num);
        TextView distance = (TextView) view.findViewById(R.id.distance);

        departure.setText(ride.getLocations().get(0).getDeparture().getAddress());
        destination.setText(ride.getLocations().get(0).getDestination().getAddress());
        startTimeText.setText(startTime[0] + ":" + startTime[1]);
        endTimeText.setText(endTime[0] + ":" + endTime[1]);
        date.setText(dateAndEndTime[0]);
        cost.setText(Double.toString(ride.getTotalCost()));
        passengerNum.setText(Integer.toString(ride.getPassengers().size()));

        double kms = 50*ride.getEstimatedTimeInMinutes()* 0.016667;
        distance.setText(Double.toString(Math.round(kms)));

        return view;
    }

    private void changeFavButton(View view) {
        Button favorite = view.findViewById(R.id.add_to_favorite);
        favorite.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(favorite.getText().toString().contains("NOT")) {
                    favorite.setBackgroundColor(Color.rgb(255,179,71));
                    favorite.setText("FAVORITE");
                }
                else {
                    favorite.setBackgroundColor(Color.rgb(237, 235, 235));
                    favorite.setText("NOT A FAVORITE");
                }
            }
        });
    }

    private List<Ride> setRides() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        User currentUser = AuthService.getCurrentUser();

        List<Ride> rides = PassengerService.getRides(currentUser.getId());
        rides.removeIf(ride -> !ride.getStatus().equals("FINISHED"));
        return rides;
    }
}
