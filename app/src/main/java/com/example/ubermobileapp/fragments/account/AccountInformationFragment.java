package com.example.ubermobileapp.fragments.account;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.models.pojo.user.User;
import com.example.ubermobileapp.services.utils.AuthService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountInformationFragment extends Fragment {
    static TextView hi;


    public AccountInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PassengerAccountFragment.
     */
    public static AccountInformationFragment newInstance() {
        return new AccountInformationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_account_information, container,
                false);
        User passenger = AuthService.getCurrentUser();
        hi = view.findViewById(R.id.hi);
        hi.setText("Hello,\n" + passenger.getName());
        getProfilePicture(view);
        return view;
    }

    public void getProfilePicture(View view) {
        User currentUser = AuthService.getCurrentUser();

        byte[] decodedString = Base64.decode(currentUser.getProfilePicture(), Base64.DEFAULT);
        Bitmap bitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        ImageView profilePicture = view.findViewById(R.id.profilePicture);
        profilePicture.setImageBitmap(bitMap);

    }

    public static void changeName(String name){
        hi.setText("Hello,\n" + name);
    }
}