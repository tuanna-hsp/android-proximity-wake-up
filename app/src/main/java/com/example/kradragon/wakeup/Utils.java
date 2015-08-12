package com.example.kradragon.wakeup;

import android.util.Log;

public final class Utils {

    public static final String TAG = "wake_up";

    private Utils() {}

    public static void debugLog(String message) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message);
        }
    }
}
