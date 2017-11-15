package com.falcon.helloandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String text = "";
        if (intent != null) {
            Object messages[] = (Object[]) bundle.get("pdus");
            String format = bundle.getString("format");
            for (int i = 0; i < messages.length; i++) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) messages[i], format);
                text += smsMessage.getMessageBody();
            }
            Toast toast = Toast.makeText(context, "Otrzymano sms:" + text, Toast.LENGTH_LONG);
            toast.show();

        }
    }
}
