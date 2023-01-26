package com.example.ubermobileapp.activities.startup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.home.DriverMainActivity;
import com.example.ubermobileapp.activities.home.PassengerMainActivity;
import com.example.ubermobileapp.activities.notification.AcceptanceRideActivity;
import com.example.ubermobileapp.androidService.NotificationService;
import com.example.ubermobileapp.models.Ride;
import com.example.ubermobileapp.models.enumeration.RideStatus;
import com.example.ubermobileapp.models.passenger.Passenger;
import com.example.ubermobileapp.models.pojo.login.LoginRequest;
import com.example.ubermobileapp.models.pojo.login.LoginResponse;
import com.example.ubermobileapp.models.pojo.user.Role;
import com.example.ubermobileapp.services.utils.ApiUtils;
import com.example.ubermobileapp.services.utils.AuthService;
import com.example.ubermobileapp.tools.Mockup;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginActivity extends AppCompatActivity {

    EditText username, password;
    private static final String CHANNEL_ID = "Zero channel";

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
        createNotificationChannel();
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
                    for (Role r: AuthService.getCurrentUser().getRoles()) {
                        switch (r.getName()) {
                            case "ROLE_DRIVER": {
                                Toast.makeText(UserLoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(UserLoginActivity.this, DriverMainActivity.class);
                                startActivity(intent);
                                // add notification
                                createDriverNotification();
                                break;
                            }
                            case "ROLE_PASSENGER": {
                                Toast.makeText(UserLoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(UserLoginActivity.this, PassengerMainActivity.class);
                                startActivity(intent);
                                // check if necessary for receive notification
                                isRideAccepted();
                                break;
                            }
                            case "ROLE_ADMIN":
                                AuthService.logout();
                                Toast.makeText(UserLoginActivity.this, "Admin cannot log in", Toast.LENGTH_LONG).show();
                                return;
                        }
                    }

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

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification channel";
            String description = "Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createDriverNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                UserLoginActivity.this, CHANNEL_ID);
        builder.setContentIntent(createDriverNotificationIntent())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("New ride")
                .setContentText("You have a new notification!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(UserLoginActivity.this);
        notificationManager.notify(1, builder.build());
    }

    private PendingIntent createDriverNotificationIntent() {
        Intent notifyIntent = new Intent(this, AcceptanceRideActivity.class);
        // Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        // Create the PendingIntent
        return PendingIntent.getActivity(
                this, 0, notifyIntent, PendingIntent.FLAG_IMMUTABLE);
    }

    private void createPassengerNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                UserLoginActivity.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Notification")
                .setContentText("Your ride accepted!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(UserLoginActivity.this);
        notificationManager.notify(1, builder.build());
    }

    private void isRideAccepted() {
        for (Ride ride: Mockup.getRides()) {
            for (Passenger passenger : ride.getPassengers()) {
                if (passenger.getEmail().equals(AuthService.getCurrentUser().getEmail()) &&
                        ride.getStatus() == RideStatus.ACCEPTED) {
                    // notification for accepted ride
                    createPassengerNotification();
                    // notification for arrived vehicle
                    startService(new Intent(this, NotificationService.class));
                    // TODO prebaci se na passenger ride activity
                    break;
                }
            }
        }
    }
}