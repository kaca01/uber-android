package com.example.ubermobileapp.fragments.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.models.login.User;
import com.example.ubermobileapp.services.utils.AuthService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PassengerAccountInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PassengerAccountInformationFragment extends Fragment {
    AlertDialog alertDialog;
    private View view;
    int SELECT_IMAGE_CODE = 1;
    ImageView imgGallery;

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
        view =  inflater.inflate(R.layout.fragment_passenger_account_information, container,
                false);

        setAccountData();

        ImageButton edit = view.findViewById(R.id.buttonEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createEditDialog();
            }
        });

        return view;
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

        imgGallery = view.findViewById(R.id.profilePicture);

        dialog.setView(newView)
                .setTitle("Edit account information")
                .setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
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

    private void setAccountData() {
        User passenger = AuthService.getCurrentUser();

        TextView name = view.findViewById(R.id.name);
        TextView postalAddress = view.findViewById(R.id.address);
        TextView email = view.findViewById(R.id.email);
        TextView phone = view.findViewById(R.id.phoneNumber);

        String firstAndLastName = passenger.getName() + " " + passenger.getSurname();
        name.setText(firstAndLastName);
        postalAddress.setText(passenger.getAddress());
        email.setText(passenger.getEmail());
        phone.setText(passenger.getTelephoneNumber());
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
            assert data != null;
            imgGallery.setImageURI(data.getData());

        }
    }
}