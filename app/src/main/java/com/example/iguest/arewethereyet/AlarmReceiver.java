package com.example.iguest.arewethereyet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Hillary on 2/23/15.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String showString = intent.getExtras().getString("showString");
        String phoneNo = intent.getExtras().getString("phone");
        Log.i("AlarmReceiver", "Alarm Received");
        Toast.makeText(context, phoneNo + ": " + showString, Toast.LENGTH_LONG).show();
    }
}
