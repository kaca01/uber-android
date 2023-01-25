package com.example.ubermobileapp.activities.history;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.account.DriverAccountActivity;
import com.example.ubermobileapp.activities.home.DriverMainActivity;
import com.example.ubermobileapp.activities.inbox.DriverInboxActivity;
import com.example.ubermobileapp.fragments.history.CommentsFragment;
import com.example.ubermobileapp.fragments.history.RatingFragment;
import com.example.ubermobileapp.models.pojo.communication.Review;
import com.example.ubermobileapp.models.pojo.communication.ReviewList;
import com.example.ubermobileapp.models.pojo.user.Passenger;
import com.example.ubermobileapp.services.implementation.ReviewService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class DriverRideInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_ride_info);

        // set data
        TextView date = findViewById(R.id.date);
        date.setText(getIntent().getStringExtra("date"));
        TextView startTime = findViewById(R.id.start_time);
        startTime.setText(getIntent().getStringExtra("start_time"));
        TextView endTime = findViewById(R.id.end_time);
        endTime.setText(getIntent().getStringExtra("end_time"));
        TextView distance = findViewById(R.id.distance);
        distance.setText(getIntent().getStringExtra("distance"));
        TextView cost = findViewById(R.id.cost);
        cost.setText(getIntent().getStringExtra("cost"));
        TextView departure = findViewById(R.id.departure);
        departure.setText(getIntent().getStringExtra("departure"));
        TextView destination = findViewById(R.id.destination);
        destination.setText(getIntent().getStringExtra("destination"));
        TextView name = findViewById(R.id.name);
        name.setText(getIntent().getStringExtra("name"));

        String rideId = this.getIntent().getStringExtra("rideId");
        ArrayList<ReviewList> reviews = (ArrayList<ReviewList>) ReviewService.getRideReviews(Long.parseLong(rideId));

        if(!reviews.isEmpty()) addRatingFragment(reviews);

        BottomNavigationView navigation = findViewById(R.id.bottom_nav);
        navigation.setSelectedItemId(R.id.page_history);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_map:
                        startActivity(new Intent(DriverRideInfoActivity.this, DriverMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_history:
                        startActivity(new Intent(DriverRideInfoActivity.this, DriverRideHistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_inbox:
                        startActivity(new Intent(DriverRideInfoActivity.this, DriverInboxActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_account:
                        startActivity(new Intent(DriverRideInfoActivity.this, DriverAccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DriverRideInfoActivity.this, DriverRideHistoryActivity.class));
        overridePendingTransition(0,0);
    }

    private void addRatingFragment(ArrayList<ReviewList> reviews) {
        // remove ADD REVIEW button
        TextView noRating = (TextView) findViewById(R.id.no_rating);
        noRating.setVisibility(View.GONE);

        // add rating and reviews fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        RatingFragment rating = new RatingFragment(reviews);
        fragmentTransaction.add(R.id.review, rating);

        List<Review> commentsReviews = new ArrayList<>();
        for (ReviewList review: reviews) {
            if(review.getDriverReview().getComment() != null &&
                    !review.getDriverReview().getComment().equals("")) commentsReviews.add(review.getDriverReview());
            if(review.getVehicleReview().getComment() != null &&
                    !review.getVehicleReview().getComment().equals("")) commentsReviews.add(review.getVehicleReview());
        }

        CommentsFragment comments = new CommentsFragment(commentsReviews);
        fragmentTransaction.add(R.id.comments, comments);
        fragmentTransaction.commit();
    }
}