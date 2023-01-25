package com.example.ubermobileapp.fragments.review;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.models.pojo.communication.Review;
import com.example.ubermobileapp.services.implementation.ReviewService;


public class LeavingReviewFragment extends DialogFragment {

    private Long rideId;

    public LeavingReviewFragment(String rideId) {
        this.rideId = Long.parseLong(rideId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaving_review, container, false);

        Button clickButton = (Button) view.findViewById(R.id.later);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        Button doneButton = (Button) view.findViewById(R.id.done);
        doneButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText driverInput = (EditText) view.findViewById(R.id.driver_comment);
                String driverCom = driverInput.getText().toString().trim();

                RatingBar driverRatingBar = view.findViewById(R.id.driver_rating_bar);
                int driverRating = (int) (driverRatingBar.getRating());

                EditText vehicleInput = (EditText) view.findViewById(R.id.vehicle_comment);
                String vehicleCom = vehicleInput.getText().toString().trim();

                RatingBar vehicleRatingBar = view.findViewById(R.id.vehicle_rating_bar);
                int vehicleRating = (int) (vehicleRatingBar.getRating());

                if(driverRating == 0 || vehicleRating == 0)
                    Toast.makeText(getActivity(), "Please enter rating", Toast.LENGTH_SHORT).show();

                else {
                    Review driver = new Review(driverRating, driverCom);
                    ReviewService.addDriverReview(rideId, driver);

                    Review vehicle = new Review(vehicleRating, vehicleCom);
                    ReviewService.addVehicleReview(rideId, vehicle);

                    getDialog().dismiss();

                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                }
            }
        });
        return view;
    }
}
