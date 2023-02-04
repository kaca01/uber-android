package com.example.ubermobileapp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.StrictMode;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.fragments.dialogs.OrderFavRideFragment;
import com.example.ubermobileapp.fragments.dialogs.OrderHistoryRideFragment;
import com.example.ubermobileapp.models.pojo.ride.FavoriteOrder;
import com.example.ubermobileapp.models.pojo.ride.Ride;
import com.example.ubermobileapp.models.pojo.user.User;
import com.example.ubermobileapp.services.implementation.PassengerService;
import com.example.ubermobileapp.services.implementation.RideService;
import com.example.ubermobileapp.services.utils.AuthService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PassengerRideAdapter extends BaseAdapter {

    private Activity activity;
    private List<Ride> rides = setRides();
    private String favoriteName;

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

        Ride ride = rides.get(position);

        addToFavorite(view, ride);
        orderRide(view, ride);

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

    private void addToFavorite(View view, Ride ride) {
        Button favorite = view.findViewById(R.id.add_to_favorite);
        favorite.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteDialog(view, ride);
            }
        });
    }

    private List<Ride> setRides() {
        User currentUser = getCurrentUser();
        List<Ride> rides = PassengerService.getRides(currentUser.getId());
        rides.removeIf(ride -> !ride.getStatus().equals("FINISHED"));
        //rides.sort(Comparator.comparing(Ride::getEndTime).reversed());
        return rides;
    }

    private void favoriteDialog(View view, Ride ride){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Add favorite ride");

        final EditText input = new EditText(view.getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Add name for your favorite ride...");
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                favoriteName = input.getText().toString().trim();
                if (favoriteName.equals("")) Toast.makeText(view.getContext(),
                        "Error: You must type a name!", Toast.LENGTH_SHORT).show();
                else {
                    User currentUser = getCurrentUser();
                    FavoriteOrder order = new FavoriteOrder(favoriteName,
                            ride.getVehicleType(),
                            new ArrayList<>(),
                            ride.isBabyTransport(),
                            ride.isPetTransport(),
                            ride.getLocations(), currentUser);

                    FavoriteOrder fav = RideService.addFavorite(order);

                    Toast.makeText(view.getContext(), "Successfully added!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private User getCurrentUser() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return AuthService.getCurrentUser();
    }

    private void orderRide(View view, Ride ride) {
        Button orderBtn = (Button) view.findViewById(R.id.order_ride);
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity act = (FragmentActivity)(activity);
                FragmentManager fm = act.getSupportFragmentManager();
                OrderHistoryRideFragment orderRideFragment = new OrderHistoryRideFragment(ride);
                orderRideFragment.show(fm, "order_ride");
            }
        });
    }
}
