package com.example.ubermobileapp.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.model.passenger.Passenger;
import com.example.ubermobileapp.tools.Mockup;

public class PassengerAdapter extends BaseAdapter {
   private Activity activity;
    public PassengerAdapter(Activity activity) {this.activity = activity;}

    @Override
    public int getCount() {
        return Mockup.getPassengers().size();
    }

    @Override
    public Object getItem(int i) {
        return Mockup.getPassengers().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = convertView;
        Passenger passenger = Mockup.getPassengers().get(i);

        if (convertView == null) view = activity.getLayoutInflater().inflate(
                R.layout.fragment_passenger_account_information, null);

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView postalAddress = (TextView) view.findViewById(R.id.address);
        TextView email = (TextView) view.findViewById(R.id.email);
        TextView phone = (TextView) view.findViewById(R.id.phoneNumber);

        String firstAndLastName = passenger.getFirstName() + " " + passenger.getLastName();
        name.setText(firstAndLastName);
        postalAddress.setText(passenger.getPostalAddress());
        email.setText(passenger.getEmail());
        phone.setText(passenger.getNumber());

        return view;
    }
}
