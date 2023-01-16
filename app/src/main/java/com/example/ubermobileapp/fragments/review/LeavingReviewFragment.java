package com.example.ubermobileapp.fragments.review;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.home.PassengerMainActivity;
import com.example.ubermobileapp.activities.inbox.ChatActivity;


public class LeavingReviewFragment extends DialogFragment {

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
                getDialog().dismiss();
                Intent intent = new Intent(getActivity(), PassengerMainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
