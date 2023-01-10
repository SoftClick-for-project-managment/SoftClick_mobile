package com.job.softclick_mobile.services.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;
import com.job.softclick_mobile.jobs.PushNotificationJobIntentService;
import com.job.softclick_mobile.utils.PushNotificationHub;
import com.microsoft.windowsazure.messaging.notificationhubs.NotificationListener;

import java.util.Map;

public class PushNotificationListener implements NotificationListener {
    @Override
    public void onPushNotificationReceived(Context context, RemoteMessage message) {
        RemoteMessage.Notification notification = message.getNotification();
        String title = notification.getTitle();
        String body = notification.getBody();
        Map<String, String> data = message.getData();

        Intent serviceIntent = new Intent();
        serviceIntent.putExtra("title", title);
        serviceIntent.putExtra("body", body);
        // Set intent data and extras
        PushNotificationJobIntentService.enqueueWork(context, serviceIntent);

        if (message != null) {
            Log.d("DEBUG", "Message Notification Title: " + title);
            Log.d("DEBUG", "Message Notification Body: " + message);
        }

        if (data != null) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                Log.d("DEBUG", "key, " + entry.getKey() + " value " + entry.getValue());
            }
        }
    }
}
