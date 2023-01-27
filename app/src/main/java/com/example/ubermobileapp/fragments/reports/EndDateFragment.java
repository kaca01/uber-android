package com.example.ubermobileapp.fragments.reports;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ubermobileapp.R;


public class EndDateFragment extends Fragment {



    public EndDateFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static EndDateFragment newInstance(String param1, String param2) {
        return new EndDateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_end_date, container, false);
    }
}