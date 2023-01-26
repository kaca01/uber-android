package com.example.ubermobileapp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.models.pojo.GenericList;
import com.example.ubermobileapp.models.pojo.ride.FavoriteOrder;
import com.example.ubermobileapp.services.implementation.RideService;
import com.example.ubermobileapp.services.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

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

        delete(view, ride.getId());

        return view;
    }

    private void delete(View view, Long rideId) {
        Button button = (Button) view.findViewById(R.id.remove1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                Call<Boolean> rideResponseCall = ApiUtils.getRideService().deleteFavorite(rideId);
                try {
                    Response<Boolean> response = rideResponseCall.execute();
                    if(response.code() == 204) {
                        activity.finish();
                        activity.startActivity(activity.getIntent());
                    }
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }
}
