package com.example.ubermobileapp.fragments.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.home.PassengerMainActivity;
import com.example.ubermobileapp.model.Message;
import com.example.ubermobileapp.services.utils.AuthService;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class CreateRide2Fragment extends Fragment {
    Spinner spin;
    ChipGroup chipGroup;
    EditText email;
    ArrayList<String> emails = new ArrayList<>();

    public CreateRide2Fragment() {}

    public static CreateRide2Fragment newInstance() { return new CreateRide2Fragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_ride2, container, false);

        chipGroup = (ChipGroup)view.findViewById(R.id.chip_group);
        email = (EditText)view.findViewById(R.id.new_passenger);
        email.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String emailText = email.getText().toString();
                    if (emailText.trim().equals("")) return false;
                    setChips(emailText);
                    email.setText("");
                }
                return false;
            }
        });

        spin = (Spinner)view.findViewById(R.id.vehicle_type_spinner);
        String[] options = { "STANDARD", "LUXURY", "VAN"};
        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, options);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0)
                {

                } else if (position==1)
                {

                }
                else if(position==2){

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        Button confirm = view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PassengerMainActivity.order.setVehicleType(spin.getSelectedItemPosition());
                PassengerMainActivity.order.setEmails(emails);
                System.out.println("HEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEJ");
                System.out.println(emails);
                PassengerMainActivity.order.getEmails().add(AuthService.getCurrentUser().getEmail());
                System.out.println("POZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
                System.out.println(emails);
                ((PassengerMainActivity)getActivity()).changeToThirdFragment();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setParams();
    }

    private void setParams(){
        spin.setSelection(PassengerMainActivity.order.getVehicleType());
        emails = new ArrayList<>();
        for(String e : PassengerMainActivity.order.getEmails()) setChips(e);
    }

    private void setChips(String e) {
        final Chip chip = (Chip) this.getLayoutInflater().inflate(R.layout.email_chip_layout, chipGroup, false);
        chip.setText(e);

        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chipGroup.removeView(chip);
                emails.remove(chip.getText().toString());
            }
        });
        chipGroup.addView(chip);
        emails.add(e);
    }
}