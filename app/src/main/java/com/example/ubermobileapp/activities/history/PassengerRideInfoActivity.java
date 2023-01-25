package com.example.ubermobileapp.activities.history;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.account.PassengerAccountActivity;
import com.example.ubermobileapp.activities.home.PassengerMainActivity;
import com.example.ubermobileapp.activities.inbox.PassengerInboxActivity;
import com.example.ubermobileapp.fragments.history.CommentsFragment;
import com.example.ubermobileapp.fragments.history.RatingFragment;
import com.example.ubermobileapp.fragments.review.LeavingReviewFragment;
import com.example.ubermobileapp.models.pojo.communication.Review;
import com.example.ubermobileapp.models.pojo.communication.ReviewList;
import com.example.ubermobileapp.models.pojo.user.User;
import com.example.ubermobileapp.services.implementation.ReviewService;
import com.example.ubermobileapp.services.utils.AuthService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PassengerRideInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_ride_info);

        setData();

        // all reviews for the selected ride
        String rideId = this.getIntent().getStringExtra("rideId");
        ArrayList<ReviewList> reviews = (ArrayList<ReviewList>) ReviewService.getRideReviews(Long.parseLong(rideId));

        boolean isPassed = isPassed3Days(getIntent().getStringExtra("date"));

        if(reviews.isEmpty())
            openDialog(isPassed);
        else
            addRatingFragment(reviews, isPassed);

        // navigation
        BottomNavigationView navigation = findViewById(R.id.bottom_nav);
        navigation.setSelectedItemId(R.id.page_history);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_map:
                        startActivity(new Intent(PassengerRideInfoActivity.this, PassengerMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_history:
                        startActivity(new Intent(PassengerRideInfoActivity.this, PassengerRideHistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_inbox:
                        startActivity(new Intent(PassengerRideInfoActivity.this, PassengerInboxActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.page_account:
                        startActivity(new Intent(PassengerRideInfoActivity.this, PassengerAccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PassengerRideInfoActivity.this, PassengerRideHistoryActivity.class));
        overridePendingTransition(0,0);
    }

    private void setData() {
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
        TextView driver = findViewById(R.id.driver);
        driver.setText(getIntent().getStringExtra("driver"));
    }

    private void openDialog(boolean isPassed) {
        if(!isPassed) {
            removeNoRatingsText();

            Button clickButton = (Button) findViewById(R.id.add_review);
            clickButton.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // show fragment for rating
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    LeavingReviewFragment leavingReviewFragment = new LeavingReviewFragment(getIntent().getStringExtra("rideId"));
                    leavingReviewFragment.show(fragmentManager, "leaving_review");
                }
            });
        } else removeAddReviewButton();
    }

    private void addRatingFragment(ArrayList<ReviewList> reviews, boolean isPassed) {
        removeNoRatingsText();

        User currentUser = AuthService.getCurrentUser();
        boolean isRated = false;

        List<Review> commentsReviews = new ArrayList<>();
        for (ReviewList review: reviews) {
            if (review.getDriverReview().getPassenger().getId().equals(currentUser.getId()))
                isRated = true;
            if(review.getDriverReview().getComment() != null &&
                    !review.getDriverReview().getComment().equals(""))
                commentsReviews.add(review.getDriverReview());
            if (review.getVehicleReview().getPassenger().getId().equals(currentUser.getId()))
                isRated = true;
            if(review.getVehicleReview().getComment() != null &&
                    !review.getVehicleReview().getComment().equals(""))
                commentsReviews.add(review.getVehicleReview());
        }

        if (isPassed) removeAddReviewButton();
        else {
            if (isRated) removeAddReviewButton();
            else openDialog(false);
        }

        // add rating and reviews fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        RatingFragment rating = new RatingFragment(reviews);
        fragmentTransaction.add(R.id.review, rating);

        CommentsFragment comments = new CommentsFragment(commentsReviews);
        fragmentTransaction.add(R.id.comments, comments);
        fragmentTransaction.commit();
    }

    private boolean isPassed3Days(String rideDate) {
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(rideDate);
            long diff = (new Date()).getTime() - date.getTime();
            System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) > 4;

        } catch (ParseException e) {
            return true;
        }
    }

    private void removeAddReviewButton() {
        // remove ADD REVIEW button
        Button addReview = (Button) findViewById(R.id.add_review);
        addReview.setVisibility(View.GONE);
    }

    private void removeNoRatingsText() {
        TextView noRating = (TextView) findViewById(R.id.no_rating);
        noRating.setVisibility(View.GONE);
    }
}