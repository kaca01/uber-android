package com.example.ubermobileapp.tools;

import android.os.Handler;
import android.widget.TextView;

import java.util.Locale;

public class Timer {

    private boolean running;
    private int seconds;
    private boolean wasRunning;
    private TextView textView;
    private static Timer instance = null;

    public static Timer getInstance() {
        if (instance == null) {
            instance = new Timer();
        }
        return instance;
    }

    public static void setInstance() {
        instance = null;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void setWasRunning(boolean wasRunning) {
        this.wasRunning = wasRunning;
    }

    public int getSeconds() {
        return seconds;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isWasRunning() {
        return wasRunning;
    }

    public void onClickStart()
    {
        running = true;
    }

    public void onClickStop()
    {
        running = false;
    }

    public void onClickReset()
    {
        // TODO : ride finishes here
        // the place where this function is called is where we should write to database
        running = false;
        seconds = 0;
    }

    // Sets the Number of seconds on the timer.
    // The runTimer() method uses a Handler
    // to increment the seconds and
    // update the text view.
    public void run()
    {
        final TextView timeView = textView;

        final Handler handler = new Handler();

        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
        handler.post(new Runnable() {
            @Override

            public void run()
            {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                // Format the seconds into hours, minutes,
                // and seconds.
                String time
                        = String
                        .format(Locale.getDefault(),
                                "%d:%02d:%02d", hours,
                                minutes, secs);

                timeView.setText(time);

                // If running is true, increment the
                // seconds variable.
                if (running) {
                    seconds++;
                }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000);
            }
        });
    }

}
