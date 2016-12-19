package com.dax.demo.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("id",0);
        Toast.makeText(context,"onReceive-"+id,Toast.LENGTH_SHORT).show();
        NotifycationHelper.showStartGangupNotification(context,id);
    }
}
