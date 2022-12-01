package com.example.ubermobileapp.activities.startup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.home.PassengerMainActivity;

public class PassengerRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_register);

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
                Intent i = new Intent(PassengerRegisterActivity.this, PassengerMainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PassengerRegisterActivity.this, UserLoginActivity.class));
        overridePendingTransition(0,0);
    }
}