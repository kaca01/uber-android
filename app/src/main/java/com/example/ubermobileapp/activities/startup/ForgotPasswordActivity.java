package com.example.ubermobileapp.activities.startup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.services.utils.ApiUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText emailInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailInput = findViewById(R.id.enter_email);

        TextView login = findViewById(R.id.toLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ForgotPasswordActivity.this, UserLoginActivity.class);
                startActivity(i);
            }
        });

        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString().trim();

                if(!email.equals(""))
                    sendMail();
                else
                    Toast.makeText(ForgotPasswordActivity.this, "Enter email!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ForgotPasswordActivity.this, UserLoginActivity.class));
        overridePendingTransition(0,0);
    }

    private void sendMail() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Call<Boolean> resCall = ApiUtils.getUserService().sendMail(emailInput.getText().toString().trim());

        try {
            Response<Boolean> res = resCall.execute();
            int statusCode = res.code();

            if(statusCode == 204) {
                Toast.makeText(ForgotPasswordActivity.this, "Please check Your email!", Toast.LENGTH_SHORT).show();
                moveToResetPassword();
            }
            else
                Toast.makeText(ForgotPasswordActivity.this, "Invalid email!", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveToResetPassword() {
        Intent i = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
        // send user email to reset password activity
        Bundle bundle = new Bundle();
        bundle.putString("email", emailInput.getText().toString().trim());
        i.putExtras(bundle);
        startActivity(i);
    }
}