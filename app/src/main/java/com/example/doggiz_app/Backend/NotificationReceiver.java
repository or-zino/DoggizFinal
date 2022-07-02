package com.example.doggiz_app.Backend;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.doggiz_app.R;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = "You have a notification, come check it out";
        String description = "Click to start";
        Bundle extras = intent.getExtras();
        if(extras !=null) {
            title = extras.getString("title");
            description = extras.getString("description");
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notification")
                .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(101, builder.build());
    }
}
