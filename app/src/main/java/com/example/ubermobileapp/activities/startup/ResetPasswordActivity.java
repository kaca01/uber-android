package com.example.ubermobileapp.activities.startup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.models.pojo.login.ResetPassword;
import com.example.ubermobileapp.services.utils.ApiUtils;
import com.example.ubermobileapp.services.utils.AuthService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText newPasswordInput, codeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");

        newPasswordInput = findViewById(R.id.new_password);
        codeInput = findViewById(R.id.verification_code);

        Button submit = findViewById(R.id.save);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPasswordInput.getText().toString().trim();
                String code = codeInput.getText().toString().trim();
                ResetPassword resetPassword = new ResetPassword(newPassword, code);

                if(!newPassword.equals("") && !code.equals(""))
                    doResetPassword(email, resetPassword);
                else
                    Toast.makeText(ResetPasswordActivity.this, "Input fields!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ResetPasswordActivity.this, ForgotPasswordActivity.class));
        overridePendingTransition(0,0);
    }

    private void doResetPassword(String email, ResetPassword resetPassword) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Call<Boolean> resCall = ApiUtils.getUserService().resetPassword(email, resetPassword);

        try {
            Response<Boolean> res = resCall.execute();
            int statusCode = res.code();

            if(statusCode == 204) {
                Toast.makeText(ResetPasswordActivity.this, "Successfully reset password!", Toast.LENGTH_SHORT).show();
                AuthService.logout();
                Intent i = new Intent(ResetPasswordActivity.this, UserLoginActivity.class);
                startActivity(i);
            }
            else
                Toast.makeText(ResetPasswordActivity.this, "Code is expired or not correct!", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}