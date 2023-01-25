package com.example.ubermobileapp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.models.pojo.communication.Review;
import com.example.ubermobileapp.models.pojo.user.Passenger;
import com.example.ubermobileapp.services.implementation.PassengerService;

import java.util.List;

public class CommentAdapter extends BaseAdapter {

    private Activity activity;
    private List<Review> reviews;

    public CommentAdapter(Activity activity, List<Review> reviews) {
        this.activity = activity;
        this.reviews = reviews;
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int position) {
        return reviews.get(position);
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
            view = activity.getLayoutInflater().inflate(R.layout.fragment_comments, null);

            Review review = reviews.get(position);

            Passenger passenger = PassengerService.getPassenger(review.getPassenger().getId());

            TextView name = (TextView) view.findViewById(R.id.passenger);
            TextView comment = (TextView) view.findViewById(R.id.comment);

            comment.setText(review.getComment());
            name.setText(passenger.getName() + " " + passenger.getSurname());

        return view;
    }
}
