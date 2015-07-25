package com.example.kradragon.wakeup;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class WakeUpService extends Service {

    private static final int NOTIFICATION_ID = 1;

    public WakeUpService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Notification notification = new Notification.Builder(getApplication())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notification_message))
                .build();
        startForeground(NOTIFICATION_ID, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
