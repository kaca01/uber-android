package com.example.ubermobileapp.fragments.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ubermobileapp.activities.favorite_ride.PassengerFavoriteRoutesActivity;
import com.example.ubermobileapp.activities.reports.PassengerReportsActivity;
import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.startup.UserLoginActivity;
import com.example.ubermobileapp.model.passenger.CreditCard;
import com.example.ubermobileapp.services.utils.AuthService;
import com.example.ubermobileapp.tools.Mockup;

public class PassengerAccountOptionsFragment extends Fragment {
    AlertDialog alertDialog;
    AlertDialog startDateDialog;
    AlertDialog endDateDialog;
    AlertDialog reportTypeDialog;
    AlertDialog reportDialog;

    public PassengerAccountOptionsFragment() {
        // Required empty public constructor
    }

    public static PassengerAccountOptionsFragment newInstance() {
        return new PassengerAccountOptionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_passenger_account_options, container,
                false);

        CardView favoriteRoutes = view.findViewById(R.id.firstCard);
        favoriteRoutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PassengerFavoriteRoutesActivity.class));
            }
        });

        CardView creditCard = view.findViewById(R.id.secondCard);
        creditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               createCreditCardDialog();
            }
        });

        CardView reports = view.findViewById(R.id.thirdCard);
        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createStartDateDialog();
            }
        });

        CardView logOut = view.findViewById(R.id.fourthCard);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthService.logout();
                startActivity(new Intent(getActivity(), UserLoginActivity.class));
            }
        });

        return view;
    }


    protected void createCreditCardDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View newView = inflater.inflate(R.layout.fragment_credit_card, null);

        dialog.setView(newView)
                .setTitle("Credit Card")
                .setCancelable(true)
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
        setCreditCardData(newView, "111-222-333");
        alertDialog = dialog.create();
        alertDialog.show();
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
                        startActivity(new Intent(getActivity(), PassengerReportsActivity.class));
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


    private void setCreditCardData(@NonNull View newView, String cardNumber) {
        CreditCard creditCard = Mockup.getCreditCard(cardNumber);

        if (creditCard == null) return;

        TextView cardType = newView.findViewById(R.id.cardType);
        TextView cardNumberView = newView.findViewById(R.id.cardNumber);
        TextView currentAmount = newView.findViewById(R.id.currentAmount);

        cardType.setText(creditCard.getType().toString());
        cardNumberView.setText(cardNumber);
        currentAmount.setText(Double.toString(creditCard.getAmount()));

    }

}