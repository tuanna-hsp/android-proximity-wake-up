package com.example.kradragon.wakeup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;


public class MainActivity extends Activity implements CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch wakeUpSwitch = (Switch) findViewById(R.id.wake_up_switch);
        wakeUpSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Intent i = new Intent(this, WakeUpService.class);
        if (isChecked) {
            startService(i);
        } else {
            stopService(i);
        }
    }
}
