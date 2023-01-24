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
import com.example.ubermobileapp.models.pojo.ride.RideList;
import com.example.ubermobileapp.models.pojo.user.Passenger;
import com.example.ubermobileapp.models.pojo.user.User;
import com.example.ubermobileapp.services.implementation.DriverService;
import com.example.ubermobileapp.services.implementation.PassengerService;
import com.example.ubermobileapp.services.utils.ApiUtils;
import com.example.ubermobileapp.services.utils.AuthService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


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

        if(getActivity().toString().contains("DriverRideHistoryActivity")) {
            setDataForDriver(currentUser, position);
        }

//        Ride ride = rides.get(position);
//        Passenger passenger = new Passenger();
//
//        if(getActivity().toString().contains("DriverRideHistoryActivity")) {
//            passenger = PassengerService.getPassenger(ride.getPassengers().get(0).getId());
//        }
//        ArrayList<Review> reviews = ride.getReviews();
//        Intent intent;
//
//        if(getActivity().toString().contains("DriverRideHistoryActivity"))
//            intent = new Intent(getActivity(), DriverRideInfoActivity.class);
//        else
//            intent = new Intent(getActivity(), PassengerRideInfoActivity.class);
//
//        System.out.println("LAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//        System.out.println(ride.getPassengers().get(0));
//
//        Bundle bundle = new Bundle();
//
//        String[] dateAndStartTime = ride.getStartTime().split("T");
//        String[] dateAndEndTime = ride.getEndTime().split("T");
//        String[] startTime = dateAndStartTime[1].split(":");
//        String[] endTime = dateAndEndTime[1].split(":");
//
//        intent.putExtra("date", dateAndStartTime[0]);
//        intent.putExtra("start_time", startTime[0] + ":" + startTime[1]);
//        intent.putExtra("end_time", endTime[0] + ":" + endTime[1]);
//        double kms = 50*ride.getEstimatedTimeInMinutes()* 0.016667;
//        intent.putExtra("distance", Double.toString(Math.round(kms)));
//        intent.putExtra("cost", Double.toString(ride.getTotalCost()));
//        intent.putExtra("departure", ride.getLocations().get(0).getDeparture().getAddress());
//        intent.putExtra("destination", ride.getLocations().get(0).getDestination().getAddress());
//        intent.putExtra("name", passenger.getName() + " " + passenger.getSurname());
//        if(ride.getReviews() == null)
//            intent.putExtra("review", "null");
//        else
//        {
//            bundle.putParcelableArrayList("review", reviews);
//            intent.putExtras(bundle);
//        }

//        startActivity(intent);
    }

    private void setDataForDriver(User currentUser, int position) {
        rides = DriverService.getRides(currentUser.getId());
        rides.removeIf(ride -> !ride.getStatus().equals("FINISHED"));

        Ride ride = rides.get(position);
        Passenger passenger = PassengerService.getPassenger(ride.getPassengers().get(0).getId());
//        ArrayList<Review> reviews = ride.getReviews();
        Intent intent = new Intent(getActivity(), DriverRideInfoActivity.class);

        Bundle bundle = new Bundle();

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
//        if(ride.getReviews() == null)
//            intent.putExtra("review", "null");
//        else
//        {
//            bundle.putParcelableArrayList("review", reviews);
//            intent.putExtras(bundle);
//        }
        startActivity(intent);
    }
}