package com.example.ubermobileapp.fragments.history;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.ListFragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ubermobileapp.activities.history.DriverRideInfoActivity;
import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.history.PassengerRideInfoActivity;
import com.example.ubermobileapp.adapters.PassengerRideAdapter;
import com.example.ubermobileapp.adapters.DriverRideAdapter;
import com.example.ubermobileapp.models.pojo.ride.Ride;
import com.example.ubermobileapp.models.pojo.user.Passenger;
import com.example.ubermobileapp.models.pojo.user.User;
import com.example.ubermobileapp.services.implementation.DriverService;
import com.example.ubermobileapp.services.implementation.PassengerService;
import com.example.ubermobileapp.services.utils.AuthService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AllRidesFragment extends ListFragment {

    private List<Ride> rides = new ArrayList<>();

    public AllRidesFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add adapter for driver
        if(getActivity().toString().contains("DriverRideHistoryActivity")) {
            DriverRideAdapter adapter = new DriverRideAdapter(getActivity());
            setListAdapter(adapter);
        }
        // Add adapter for passenger
        else {
            PassengerRideAdapter passengerRideAdapter = new PassengerRideAdapter(getActivity());
            setListAdapter(passengerRideAdapter);
        }
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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        User currentUser = AuthService.getCurrentUser();

        if(getActivity().toString().contains("DriverRideHistoryActivity"))
            setDataForDriver(currentUser, position);
        else
            setDataForPassenger(currentUser, position);
    }

    private void setDataForDriver(User currentUser, int position) {
        rides = DriverService.getRides(currentUser.getId());
        rides.removeIf(ride -> !ride.getStatus().equals("FINISHED"));
        rides.sort(Comparator.comparing(Ride::getEndTime).reversed());

        Ride ride = rides.get(position);

        Passenger passenger = PassengerService.getPassenger(ride.getPassengers().get(0).getId());
        Intent intent = new Intent(getActivity(), DriverRideInfoActivity.class);

        String[] dateAndStartTime = ride.getStartTime().split("T");
        String[] dateAndEndTime = ride.getEndTime().split("T");
        String[] startTime = dateAndStartTime[1].split(":");
        String[] endTime = dateAndEndTime[1].split(":");

        intent.putExtra("date", dateAndStartTime[0]);
        intent.putExtra("start_time", startTime[0] + ":" + startTime[1]);
        intent.putExtra("end_time", endTime[0] + ":" + endTime[1]);
        double kms = 50*ride.getEstimatedTimeInMinutes()* 0.016667;
        intent.putExtra("distance", Double.toString(Math.round(kms)));
        intent.putExtra("cost", Double.toString(ride.getTotalCost()));
        intent.putExtra("departure", ride.getLocations().get(0).getDeparture().getAddress());
        intent.putExtra("destination", ride.getLocations().get(0).getDestination().getAddress());
        intent.putExtra("name", passenger.getName() + " " + passenger.getSurname());
        intent.putExtra("rideId", Long.toString(ride.getId()));

        startActivity(intent);
    }

    private void setDataForPassenger(User currentUser, int position) {
        rides = PassengerService.getRides(currentUser.getId());
        rides.removeIf(ride -> !ride.getStatus().equals("FINISHED"));
        rides.sort(Comparator.comparing(Ride::getEndTime).reversed());

        Ride ride = rides.get(position);

        Intent intent = new Intent(getActivity(), PassengerRideInfoActivity.class);

        String[] dateAndStartTime = ride.getStartTime().split("T");
        String[] dateAndEndTime = ride.getEndTime().split("T");
        String[] startTime = dateAndStartTime[1].split(":");
        String[] endTime = dateAndEndTime[1].split(":");

        intent.putExtra("date", dateAndEndTime[0]);
        intent.putExtra("start_time", startTime[0] + ":" + startTime[1]);
        intent.putExtra("end_time", endTime[0] + ":" + endTime[1]);
        double kms = 50*ride.getEstimatedTimeInMinutes()* 0.016667;
        intent.putExtra("distance", Double.toString(Math.round(kms)));
        intent.putExtra("cost", Double.toString(ride.getTotalCost()));
        intent.putExtra("departure", ride.getLocations().get(0).getDeparture().getAddress());
        intent.putExtra("destination", ride.getLocations().get(0).getDestination().getAddress());
        intent.putExtra("name", currentUser.getName() + " " + currentUser.getSurname());
        intent.putExtra("rideId", Long.toString(ride.getId()));
        intent.putExtra("driver", ride.getDriver().getName() + " " + ride.getDriver().getSurname());

        startActivity(intent);
    }
}