package com.example.ubermobileapp.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ubermobileapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PassengerAccountInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PassengerAccountInformationFragment extends Fragment {

    public PassengerAccountInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PassengerAccountFragment.
     */
    public static PassengerAccountInformationFragment newInstance() {
        return new PassengerAccountInformationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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