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
import com.example.ubermobileapp.activities.reports.DriverReportsActivity;
import com.example.ubermobileapp.activities.reports.PassengerReportsActivity;
import com.example.ubermobileapp.models.pojo.user.User;
import com.example.ubermobileapp.services.utils.AuthService;

public class AccountOptionsFragment extends Fragment {
    AlertDialog startDateDialog;
    AlertDialog endDateDialog;
    AlertDialog alertDialog;
    private View view;
    int SELECT_IMAGE_CODE = 1;
    ImageView imgGallery;

    public AccountOptionsFragment() {
        // Required empty public constructor
    }

    public static AccountOptionsFragment newInstance(String param1, String param2) {
        return new AccountOptionsFragment();
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

        CardView reports = view.findViewById(R.id.secondCard);
        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createStartDateDialog();
            }
        });

        return view;
    }


    protected void createStartDateDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View newView = inflater.inflate(R.layout.fragment_start_date, null);

        dialog.setView(newView)
                .setTitle("Reports")
                .setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        createEndDateDialog();
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        startDateDialog = dialog.create();
        startDateDialog.show();
    }


    protected void createEndDateDialog() {
        startDateDialog.cancel();
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View newView = inflater.inflate(R.layout.fragment_end_date, null);

        dialog.setView(newView)
                .setTitle("Reports")
                .setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (AuthService.getCurrentUser().getRoles().get(0).getName().equals("ROLE_PASSENGER")){
                            startActivity(new Intent(getActivity(), PassengerReportsActivity.class));
                        }
                        else{
                            startActivity(new Intent(getActivity(), DriverReportsActivity.class));
                        }
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        endDateDialog = dialog.create();
        endDateDialog.show();
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
}