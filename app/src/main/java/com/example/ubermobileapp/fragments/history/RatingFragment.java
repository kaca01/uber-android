package com.example.ubermobileapp.fragments.history;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.models.pojo.communication.ReviewList;

import java.util.ArrayList;

public class RatingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<ReviewList> reviews = new ArrayList<>();

    private String mParam1;
    private String mParam2;

    public RatingFragment() { }

    public RatingFragment(ArrayList<ReviewList> reviews) {
        this.reviews = reviews;
    }

    public static RatingFragment newInstance(String param1, String param2) {
        RatingFragment fragment = new RatingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating, container, false);
        setDriverRating(view);
        setVehicleRating(view);
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void setDriverRating(View view) {
        RatingBar driverRating = view.findViewById(R.id.driver_rating_bar);

        int numGrades = 0;
        int allGrades = 0;
        double average = 0;
        for (ReviewList review: reviews) {
            if(review.getDriverReview() != null) {
                numGrades += 1;
                allGrades += review.getDriverReview().getRating();
            }
        }
        if(allGrades != 0) {
            average = Math.round(allGrades / numGrades * 2) / 2.0;
            driverRating.setRating((float) average);
        }

        TextView numDriverGradesText = view.findViewById(R.id.driver_rating);
        numDriverGradesText.setText(Double.toString(average));
        TextView driverGradesText = view.findViewById(R.id.num_driver_grades);
        driverGradesText.setText(" - " + Integer.toString(numGrades) + " ocene");
    }

    @SuppressLint("SetTextI18n")
    private void setVehicleRating(View view) {
        RatingBar vehicleRating = view.findViewById(R.id.vehicle_rating_bar);

        int numGrades = 0;
        int allGrades = 0;
        double average = 0;
        for (ReviewList review: reviews) {
            if(review.getVehicleReview() != null) {
                numGrades += 1;
                allGrades += review.getVehicleReview().getRating();
            }
        }
        if(allGrades != 0) {
            average = Math.round(allGrades / numGrades * 2) / 2.0;
            vehicleRating.setRating((float) average);
        }

        TextView numDriverGradesText = view.findViewById(R.id.vehicle_rating);
        numDriverGradesText.setText(Double.toString(average));
        TextView driverGradesText = view.findViewById(R.id.num_vehicle_grades);
        driverGradesText.setText(" - " + Integer.toString(numGrades) + " ocene");
    }
}