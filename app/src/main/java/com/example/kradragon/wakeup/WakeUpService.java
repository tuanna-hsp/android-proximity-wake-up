package com.example.kradragon.wakeup;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;

public class WakeUpService extends Service implements SensorEventListener {

    private static final int NOTIFICATION_ID = 1;
    private static final int SAMPLING_PERIOD_MICROSECOND = 5;

    private Sensor mProximitySensor;
    private SensorManager mSensorManager;
    private PowerManager.WakeLock mWakeLock;
    private ScreenReceiver mScreenReceiver;
    private float mMaxRange;
    private boolean mPendingWakeUp;

    public WakeUpService() {
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate() {
        super.onCreate();

        mScreenReceiver = new ScreenReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(mScreenReceiver, filter);

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        int flags = PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP;
        mWakeLock = powerManager.newWakeLock(flags, "wake_up_tag");

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mMaxRange = mProximitySensor.getMaximumRange();

        Intent i = new Intent(Constants.TURN_OFF_SCREEN_ACTION);
        PendingIntent pi = PendingIntent.getBroadcast(getApplication(), 0, i, 0);

        Notification notification = new Notification.Builder(getApplication())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notification_message))
                .setContentIntent(pi)
                .build();
        startForeground(NOTIFICATION_ID, notification);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float currentRange = sensorEvent.values[0];
        if (mMaxRange > 0.0F) {
            float checkRange = (mMaxRange * 2.0F) / 3F;
            if (currentRange <= checkRange) {
                mPendingWakeUp = true;
            }
            if (mPendingWakeUp && currentRange > checkRange) {
                mPendingWakeUp = false;
                wakePhoneUp();
            }
        } else {
            if (currentRange <= 2.0F) {
                mPendingWakeUp = true;
            }
            if (mPendingWakeUp && currentRange > 2.0F) {
                mPendingWakeUp = false;
                wakePhoneUp();
            }
        }
    }

    private void wakePhoneUp() {
        Utils.debugLog("wakePhoneUp()");

        mWakeLock.acquire();
        mWakeLock.release();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this, mProximitySensor);
        unregisterReceiver(mScreenReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case Constants.ACTION_REGISTER_LISTENER:
                    mSensorManager.registerListener(this, mProximitySensor,
                            SAMPLING_PERIOD_MICROSECOND);
                    break;
                case Constants.ACTION_UNREGISTER_LISTENER:
                    mSensorManager.unregisterListener(this, mProximitySensor);
                    break;
            }
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
