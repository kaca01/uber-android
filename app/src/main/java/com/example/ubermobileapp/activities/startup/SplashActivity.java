package com.example.ubermobileapp.activities.startup;

import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.ubermobileapp.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {

    Animation topAnim;

    TextView textView;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        lottieAnimationView = findViewById(R.id.lottie);
        textView = findViewById(R.id.head);

        // disappearing
        lottieAnimationView.animate().translationX(2500).setDuration(3000).setStartDelay(3500);

        int SPLASH_TIME_OUT = 5000;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, UserLoginActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}