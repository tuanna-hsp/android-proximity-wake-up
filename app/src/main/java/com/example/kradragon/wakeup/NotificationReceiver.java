package com.example.kradragon.wakeup;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {

    private DevicePolicyManager mPolicyManager;
    private ComponentName mAdminReceiver;

    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case Constants.TURN_OFF_SCREEN_ACTION:
                if (mPolicyManager == null) {
                    mPolicyManager = (DevicePolicyManager)
                            context.getSystemService(Context.DEVICE_POLICY_SERVICE);
                }
                if (isAdminEnable(context)) {
                    mPolicyManager.lockNow();
                } else {

                }
                break;
            default:
                break;
        }
    }

    private boolean isAdminEnable(Context context) {
        if (mPolicyManager == null) {
            mPolicyManager = (DevicePolicyManager)
                    context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        }
        if (mAdminReceiver == null) {
            mAdminReceiver = new ComponentName(context, AdminReceiver.class);
        }

        return mPolicyManager.isAdminActive(mAdminReceiver);
    }
}
