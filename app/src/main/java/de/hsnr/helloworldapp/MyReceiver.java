package de.hsnr.helloworldapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.DateFormat;
import java.util.Date;

public class MyReceiver extends BroadcastReceiver {

    private static final int ID = 42;
    private static final String CHANNEL_ID = "BCR_01";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = DateFormat.getDateInstance().format(new Date());
        Notification.Builder builder =
                new Notification.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(
                R.drawable.ic_launcher).
                setContentTitle(
                        context.getString(R.string.app_name)).
                setContentText(msg).
                setWhen(System.currentTimeMillis());
        Notification n = builder.build();
        NotificationManager m = context.getSystemService(NotificationManager.class);

        if (m != null) {
            // Kanal anlegen
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    context.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            m.createNotificationChannel(channel);
            // anzeigen
            m.notify(ID, n);
        }
    }
//hihi

    }

