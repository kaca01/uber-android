package com.example.ubermobileapp.fragments.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.model.Message;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateRide2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateRide2Fragment extends Fragment {
    Spinner spin;
    private String list[] = {};

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

        /*ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.passenger_item, list);
        ListView listView = (ListView) view.findViewById(R.id.passengers_list);
        listView.setAdapter(adapter);*/
        //umjesto arrayAdapter da bude listAdapter
        //dobaviti remove ikonu i ukloniti iz array-a...kako znati koja je kliknuta?
        // IPAK KORISTITI RECYLERVIEW
        // PROVJERITI POSTOJI LI FUNKCIJA DA SE NA DUZI KLIK OMOGUCE NOVE OPCIJE
        // MOZDA BOOJE PROVJERITI VJEZBE??

        // OK : TREBA DEFINISATI SVOJ ADAPTER, LAKSE JE NEGO SE ZAJEBAVATI :)))


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

        return view;
    }
}