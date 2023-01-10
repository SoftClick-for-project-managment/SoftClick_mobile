package com.job.softclick_mobile.jobs;

import android.content.Context;
import android.content.Intent;

import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.job.softclick_mobile.R;

public class PushNotificationJobIntentService extends JobIntentService {
    static final int JOB_ID = 1000;

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, PushNotificationJobIntentService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(Intent intent) {
        String notificationTitle = intent.getStringExtra("title");
        String notificationMessage = intent.getStringExtra("message");

        // Display notification in system tray
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyChannelId")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }
}
