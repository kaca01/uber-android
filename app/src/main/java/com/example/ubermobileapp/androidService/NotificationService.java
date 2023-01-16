package com.example.ubermobileapp.androidService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.ubermobileapp.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationService extends Service {

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotification() {
        NotificationManager mNotificationManager = (NotificationManager)getSystemService( NOTIFICATION_SERVICE ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() , default_notification_channel_id ) ;
        mBuilder.setContentTitle("Your ride");
        mBuilder.setContentText("The vehicle arrived at the address");
        mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground);
        mBuilder.setAutoCancel(true);
        if (Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT ;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME" , importance) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID) ;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        mNotificationManager.notify(1, mBuilder.build()) ;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("REZ", "SyncService onStartCommand");
        executor.execute(() -> {
            //Background work here
            Log.i("REZ", "Background work here");
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(this::createNotification);
        });
        stopSelf();
        return START_NOT_STICKY;
    }
}
