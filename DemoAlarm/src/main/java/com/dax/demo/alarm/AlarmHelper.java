package com.dax.demo.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by jerryliu on 2016/12/13.
 */

public class AlarmHelper {

    public static void setAlarm(Context context, int requestCode){
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,AlarmReceiver.class);
        intent.putExtra("id",requestCode);
        PendingIntent pi = PendingIntent.getBroadcast(context,requestCode,intent, PendingIntent.FLAG_ONE_SHOT);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+5000,pi);
    }

    public static void cancelAlarm(Context context, int requestCode){
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context,requestCode,intent, PendingIntent.FLAG_ONE_SHOT);
        am.cancel(pi);
    }
}
