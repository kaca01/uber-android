package com.example.ubermobileapp.activities.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.ubermobileapp.R;
import com.example.ubermobileapp.activities.home.DriverMainActivity;
import com.example.ubermobileapp.activities.notification.AcceptanceRideActivity;
import com.example.ubermobileapp.activities.startup.UserLoginActivity;
import com.example.ubermobileapp.androidService.AcceptingRideService;

public class NotificationReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "Zero channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("RECEIIIIIIIIIIIIIIIIIIIIIIIIIVEEEEEEEEEEEEERRRRRRRRRRRRRR");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);

        if(intent.getAction().equals(DriverMainActivity.SYNC_DATA)) {
            int resultCode = intent.getExtras().getInt(AcceptingRideService.RESULT_CODE);

            if(resultCode == 1) {
                builder.setContentIntent(createDriverNotificationIntent(context))
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("New ride")
                        .setContentText("You have a new notification!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);
            }

            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }

    }

    private PendingIntent createDriverNotificationIntent(Context context) {
        Intent notifyIntent = new Intent(context, AcceptanceRideActivity.class);
        // Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        // Create the PendingIntent
        return PendingIntent.getActivity(
                context, 0, notifyIntent, PendingIntent.FLAG_IMMUTABLE);
    }
}
