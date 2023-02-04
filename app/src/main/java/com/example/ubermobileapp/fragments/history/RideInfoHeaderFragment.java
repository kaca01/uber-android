package com.example.ubermobileapp.fragments.history;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ubermobileapp.activities.history.PassengerRideHistoryActivity;
import com.example.ubermobileapp.activities.inbox.DriverInboxActivity;
import com.example.ubermobileapp.activities.history.DriverRideHistoryActivity;
import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.inbox.PassengerInboxActivity;

public class RideInfoHeaderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RideInfoHeaderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RideInfoHeaderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RideInfoHeaderFragment newInstance(String param1, String param2) {
        RideInfoHeaderFragment fragment = new RideInfoHeaderFragment();
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

        View view = inflater.inflate(R.layout.fragment_ride_info_header, container, false);

        ImageView backArrow = view.findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                if(getActivity().toString().contains("Driver"))
                    i = new Intent(getActivity().getApplication(), DriverRideHistoryActivity.class);
                else
                    i = new Intent(getActivity().getApplication(), PassengerRideHistoryActivity.class);
                startActivity(i);
            }
        });

        ImageView chat = view.findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                if(getActivity().toString().contains("Driver"))
                    i = new Intent(getActivity().getApplication(), DriverInboxActivity.class);
                else
                    i = new Intent(getActivity().getApplication(), PassengerInboxActivity.class);
                startActivity(i);
            }
        });

        return view;
    }
}