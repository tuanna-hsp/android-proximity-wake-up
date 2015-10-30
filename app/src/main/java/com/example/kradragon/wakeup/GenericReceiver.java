package com.example.kradragon.wakeup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class GenericReceiver extends BroadcastReceiver {

    public GenericReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
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
            case Intent.ACTION_BOOT_COMPLETED:
                context.startService(i);
                break;
        }
    }
}
