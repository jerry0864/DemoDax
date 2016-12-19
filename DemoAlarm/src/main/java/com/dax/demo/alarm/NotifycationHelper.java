package com.dax.demo.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * Created by jerryliu on 2016/12/13.
 */

public class NotifycationHelper {
    public static void showStartGangupNotification(Context context,int id){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker("状态栏提示文字");
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.layout_notification_view);
        views.setTextViewText(R.id.title,"您应邀的由"+id+"发起的王者荣耀组队已到达游戏时间，马上查看");
        views.setImageViewResource(R.id.icon,R.mipmap.ic_launcher_round);
//        builder.setContentText("您应邀的由"+id+"发起的王者荣耀组队已到达游戏时间，马上查看");
//        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        Intent intent = new Intent(context,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pi = PendingIntent.getActivity(context,id,intent,PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(pi);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setAutoCancel(true);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(id,builder.setContent(views).build());
    }
}
