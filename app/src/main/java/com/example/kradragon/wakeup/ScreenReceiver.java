package com.example.kradragon.wakeup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenReceiver extends BroadcastReceiver {

    public ScreenReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Utils.debugLog("ScreenReceiver");

        Intent i = new Intent(context, WakeUpService.class);
        switch (intent.getAction()) {
            case Intent.ACTION_SCREEN_ON:
                i.setAction(Constants.ACTION_UNREGISTER_LISTENER);
                context.startService(i);
                break;
            case Intent.ACTION_SCREEN_OFF:
                i.setAction(Constants.ACTION_REGISTER_LISTENER);
                context.startService(i);
                break;
        }
    }
}
