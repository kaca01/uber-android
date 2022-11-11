package com.example.ubermobileapp.fragments;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.ubermobileapp.PassengerAccountActivity;
import com.example.ubermobileapp.PassengerMainActivity;
import com.example.ubermobileapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PassengerAccountInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PassengerAccountInformationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PassengerAccountInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PassengerAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PassengerAccountInformationFragment newInstance() {
        return new PassengerAccountInformationFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_passenger_account_information, container,
                false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // TODO do not delete this code, this is for later
        /*ImageButton imageButton = (ImageButton) getView().findViewById(R.id.buttonEdit);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PassengerMainActivity.class);
                startActivity(intent);
            }
        });*/
    }
}