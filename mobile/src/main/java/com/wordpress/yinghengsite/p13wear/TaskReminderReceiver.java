package com.wordpress.yinghengsite.p13wear;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

public class TaskReminderReceiver extends BroadcastReceiver {

    int notifReqCode = 1;

    @Override
    public void onReceive(Context context, Intent i) {

        int id = i.getIntExtra("id", -1);
        String name = i.getStringExtra("name");
        String desc = i.getStringExtra("desc");

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel("default", "Default Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription("This is for default notification");
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, notifReqCode,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);


        Intent intentreply = new Intent(context,
                MainActivity.class);
        intentreply.putExtra("taskid", id);
        PendingIntent pendingIntentReply = PendingIntent.getActivity
                (context, 0, intentreply,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        Intent addReply = new Intent(context,
                Add.class);
        PendingIntent pendingIntentadd = PendingIntent.getActivity
                (context, 1, addReply,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteInput ri = new RemoteInput.Builder("status")
                .setLabel("Status report")
                .setChoices(new String[]{"Completed", "Not yet"})
                .build();

        RemoteInput ri1 = new RemoteInput.Builder("add")
                .setLabel("Status report")
                .setChoices(new String[]{"Completed", "Not yet"})
                .build();

        NotificationCompat.Action action2 = new
                NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "Reply",
                pendingIntentReply)
                .addRemoteInput(ri)
                .build();

        NotificationCompat.Action action = new
                NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "Launch Task Manager",
                pIntent).build();

        NotificationCompat.Action action3 = new
                NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "Add",
                pendingIntentadd)
                .addRemoteInput(ri1)
                .build();

        NotificationCompat.WearableExtender extender = new
                NotificationCompat.WearableExtender();
        extender.addAction(action);
        extender.addAction(action2);
        extender.addAction(action3);

        String text = "This is a Basic Notification message";
        String title = "Wearable Notification";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
        builder.setContentTitle("Task Manager Reminder");
        builder.setContentText(name + "\n" + desc);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setContentIntent(pIntent);
        builder.setAutoCancel(true);


        builder.extend(extender);
        Notification n = builder.build();

        notificationManager.notify(notifReqCode, n);
    }

}