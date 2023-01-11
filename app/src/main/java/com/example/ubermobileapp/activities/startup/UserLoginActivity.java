package com.example.ubermobileapp.activities.startup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.home.DriverMainActivity;
import com.example.ubermobileapp.activities.home.PassengerMainActivity;
import com.example.ubermobileapp.model.login.LoginRequest;
import com.example.ubermobileapp.model.login.LoginResponse;
import com.example.ubermobileapp.model.login.Role;
import com.example.ubermobileapp.services.utils.ApiUtils;
import com.example.ubermobileapp.services.utils.AuthService;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginActivity extends AppCompatActivity {

    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        TextView forgotPassword = findViewById(R.id.forgot_password);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        Button loginButton = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(UserLoginActivity.this,"Username / Password Required", Toast.LENGTH_LONG).show();
                }else{
                    try {
                        //proceed to login
                        login();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        TextView signUpButton = findViewById(R.id.sign_up);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserLoginActivity.this, PassengerRegisterActivity.class);
                startActivity(i);
            }
        });
    }

    public void login() throws JSONException {
        LoginRequest request = new LoginRequest();
        request.setEmail(username.getText().toString());
        request.setPassword(password.getText().toString());

        Call<LoginResponse> loginResponseCall = ApiUtils.getUserService().userLogin(request);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    AuthService.setAccessToken(response.body().getAccessToken());
                    new AuthService().getMyInfo();  // load current user
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (Role r: AuthService.getCurrentUser().getRoles()) {
                                switch (r.getName()) {
                                    case "ROLE_DRIVER": {
                                        Toast.makeText(UserLoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(UserLoginActivity.this, DriverMainActivity.class);
                                        startActivity(intent);
                                        break;
                                    }
                                    case "ROLE_PASSENGER": {
                                        Toast.makeText(UserLoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(UserLoginActivity.this, PassengerMainActivity.class);
                                        startActivity(intent);
                                        break;
                                    }
                                    case "ROLE_ADMIN":
                                        AuthService.logout();
                                        Toast.makeText(UserLoginActivity.this, "Admin cannot log in", Toast.LENGTH_LONG).show();
                                        return;
                                }
                            }
                        }
                    }, 700);

                } else
                    Toast.makeText(UserLoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(UserLoginActivity.this, "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}