package com.example.ubermobileapp.activities.startup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.home.PassengerMainActivity;
import com.example.ubermobileapp.models.pojo.user.Passenger;
import com.example.ubermobileapp.services.implementation.PassengerService;
import com.example.ubermobileapp.services.utils.ApiUtils;

public class PassengerRegisterActivity extends AppCompatActivity {

    EditText name;
    EditText surname;
    EditText email;
    EditText password;
    EditText repeatPassword;
    EditText address;
    EditText phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_register);

        name = findViewById(R.id.txtFirstName);
        surname = findViewById(R.id.txtSecondName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        repeatPassword = findViewById(R.id.repeatPassword);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phoneReg);

        TextView login = findViewById(R.id.returnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PassengerRegisterActivity.this, UserLoginActivity.class);
                startActivity(i);
            }
        });

        Button registerButton = findViewById(R.id.registerBtn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) return;
                Passenger passenger = new Passenger(name.getText().toString(), surname.getText().toString(),
                        phone.getText().toString(), email.getText().toString(),
                        address.getText().toString(), password.getText().toString());
                passenger = PassengerService.insertPassenger(passenger);
                if(passenger.getName()==null){
                    Toast.makeText(getApplicationContext(), passenger.getEmail(), Toast.LENGTH_LONG).show();
                    return;
                }
                TextView activation = findViewById(R.id.activation);
                activation.setVisibility(View.VISIBLE);
                //Intent i = new Intent(PassengerRegisterActivity.this, PassengerMainActivity.class);
                //startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PassengerRegisterActivity.this, UserLoginActivity.class));
        overridePendingTransition(0,0);
    }

    private boolean validate(){
        if(TextUtils.isEmpty(name.getText().toString().trim()) || TextUtils.isEmpty(surname.getText().toString().trim())
        || TextUtils.isEmpty(email.getText().toString().trim()) || TextUtils.isEmpty(password.getText().toString().trim())
        || TextUtils.isEmpty(address.getText().toString().trim()) || TextUtils.isEmpty(phone.getText().toString().trim())
        || TextUtils.isEmpty(repeatPassword.getText().toString().trim())){
            Toast.makeText(PassengerRegisterActivity.this,"All fields must be filled!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!password.getText().toString().trim().equals(repeatPassword.getText().toString().trim())){
            Toast.makeText(PassengerRegisterActivity.this,"Password is not matching!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}