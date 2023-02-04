package com.example.ubermobileapp.fragments.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.home.PassengerMainActivity;
import com.example.ubermobileapp.models.pojo.user.Passenger;
import com.example.ubermobileapp.services.implementation.PassengerService;
import com.example.ubermobileapp.services.utils.ApiUtils;
import com.example.ubermobileapp.services.utils.AuthService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditAccountInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditAccountInformationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditAccountInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditAccountInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditAccountInformationFragment newInstance(String param1, String param2) {
        EditAccountInformationFragment fragment = new EditAccountInformationFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_account_information, container,
                false);

        return view;
    }
}