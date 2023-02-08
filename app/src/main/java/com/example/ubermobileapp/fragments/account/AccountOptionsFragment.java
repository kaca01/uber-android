package com.example.ubermobileapp.fragments.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.models.pojo.user.Passenger;
import com.example.ubermobileapp.models.pojo.user.User;
import com.example.ubermobileapp.services.implementation.DriverService;
import com.example.ubermobileapp.services.implementation.PassengerService;
import com.example.ubermobileapp.services.utils.AuthService;

public class AccountOptionsFragment extends Fragment {
    AlertDialog alertDialog;
    private View view;
    int SELECT_IMAGE_CODE = 1;
    ImageView imgGallery;

    public AccountOptionsFragment() {
        // Required empty public constructor
    }

    public static AccountOptionsFragment newInstance(String param1, String param2) {
        AccountOptionsFragment fragment = new AccountOptionsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_account_options, container, false);

        CardView editData = view.findViewById(R.id.firstCard);
        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createEditDialog();
            }
        });

        return view;
    }

    protected void createEditDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View newView = inflater.inflate(R.layout.fragment_edit_account_information, null);
        setEditAccountData(newView);

        Button button = newView.findViewById(R.id.btnChangePic);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Title"), SELECT_IMAGE_CODE);
            }
        });

        imgGallery = getActivity().findViewById(R.id.profilePicture);

        dialog.setView(newView)
                .setTitle("Edit account information")
                .setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        updateAccountData(newView);
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alertDialog = dialog.create();
        alertDialog.show();
    }

    private void setEditAccountData(View newView) {
        User passenger = AuthService.getCurrentUser();

        TextView name = newView.findViewById(R.id.inputName);
        TextView lastName = newView.findViewById(R.id.inputSurname);
        TextView postalAddress = newView.findViewById(R.id.postalAdressInput);
        TextView email = newView.findViewById(R.id.inputEmail);
        TextView phone = newView.findViewById(R.id.inputPhone);
        // TODO : check how user can change password
        TextView password = newView.findViewById(R.id.passwordInput);
        TextView passwordAgain = newView.findViewById(R.id.passwordInputAgain);

        name.setText(passenger.getName());
        lastName.setText(passenger.getSurname());
        postalAddress.setText(passenger.getAddress());
        email.setText(passenger.getEmail());
        phone.setText(passenger.getTelephoneNumber());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1) {
            if (data != null) imgGallery.setImageURI(data.getData());
        }
    }

    public void updateAccountData(View newView) {
        User passenger = AuthService.getCurrentUser();
        TextView name = newView.findViewById(R.id.inputName);
        passenger.setName(name.getText().toString());
        TextView surname = newView.findViewById(R.id.inputSurname);
        passenger.setSurname(surname.getText().toString());
        // TODO : work on this
        passenger.setProfilePicture("");
        TextView phone = newView.findViewById(R.id.inputPhone);
        passenger.setTelephoneNumber(phone.getText().toString());
//        // TODO : verification?
//        TextView email = newView.findViewById(R.id.inputEmail);
//        passenger.setEmail(email.getText().toString());
        TextView address = newView.findViewById(R.id.postalAdressInput);
        passenger.setAddress(address.getText().toString());

        if (AuthService.getCurrentUser().getRoles().get(0).getName().equals("ROLE_PASSENGER")){
            Passenger updatedPassenger = PassengerService.updatePassenger(passenger, passenger.getId());
            if (updatedPassenger != null) {
                setEditAccountData(newView);
                AccountInformationFragment.changeName(passenger.getName());
            }
        }
        else{
             User updateDriver = DriverService.updateDriver(passenger, passenger.getId());
            if (updateDriver != null) {
                setEditAccountData(newView);
                AccountInformationFragment.changeName(passenger.getName());
            }
        }
    }
}
