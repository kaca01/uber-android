package com.example.ubermobileapp.activities.history;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.account.DriverAccountActivity;
import com.example.ubermobileapp.activities.account.PassengerAccountActivity;
import com.example.ubermobileapp.activities.home.DriverMainActivity;
import com.example.ubermobileapp.activities.home.PassengerMainActivity;
import com.example.ubermobileapp.activities.inbox.DriverInboxActivity;
import com.example.ubermobileapp.activities.inbox.PassengerInboxActivity;
import com.example.ubermobileapp.fragments.history.CommentsFragment;
import com.example.ubermobileapp.fragments.history.RatingFragment;
import com.example.ubermobileapp.fragments.review.LeavingReviewFragment;
import com.example.ubermobileapp.model.communication.Review;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class PassengerRideInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_ride_info);

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
        ImageView map = findViewById(R.id.ride);
        map.setImageResource(getIntent().getIntExtra("path", R.drawable.map));

        ArrayList<Review> reviews = this.getIntent().getExtras().getParcelableArrayList("review");

        if(reviews == null)
            addAddReviewFragment();
        else
            addRatingFragment();

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

    private void addAddReviewFragment() {
        Button clickButton = (Button) findViewById(R.id.add_review);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                LeavingReviewFragment leavingReviewFragment = new LeavingReviewFragment();
                leavingReviewFragment.show(fragmentManager, "leaving_review");
            }
        });
    }

    private void addRatingFragment() {
        // remove ADD REVIEW button
        Button addReview = (Button) findViewById(R.id.add_review);
        addReview.setVisibility(View.GONE);

        // add rating and reviews fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RatingFragment rating = new RatingFragment();
        fragmentTransaction.add(R.id.review, rating);

        CommentsFragment comments = new CommentsFragment();
        fragmentTransaction.add(R.id.comments, comments);
        fragmentTransaction.commit();
    }
}