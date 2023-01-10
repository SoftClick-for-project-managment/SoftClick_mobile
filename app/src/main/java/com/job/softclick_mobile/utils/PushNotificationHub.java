package com.job.softclick_mobile.utils;

import android.app.Application;
import android.content.Context;
import android.telephony.ims.RegistrationManager;

import androidx.annotation.NonNull;

import com.job.softclick_mobile.models.TokenPeer;
import com.job.softclick_mobile.services.notifications.PushNotificationListener;
import com.job.softclick_mobile.services.storage.StoredUser;
import com.microsoft.windowsazure.messaging.Registration;
import com.microsoft.windowsazure.messaging.notificationhubs.NotificationHub;

public class PushNotificationHub {
    private static NotificationHub hub;
    private static Registration registration;

    public static void init(@NonNull Application app) {
        String connectionString = "Endpoint=sb://softclick-qa-notif.servicebus.windows.net/;SharedAccessKeyName=DefaultListenSharedAccessSignature;SharedAccessKey=qicaQd6IdFHvHRSfFock17vNDILxnS3f1vfIuRze8GQ=";
        String hubName = "softclick-qa-notif-hub";
        TokenPeer userTokens = StoredUser.load(app);
        if (userTokens != null) {
            NotificationHub.setListener(new PushNotificationListener());
            NotificationHub.start(app, hubName, connectionString);
            NotificationHub.setInstallationId(userTokens.getUsername());
        }
    }

//    public static void register(Context context) throws Exception {
//        TokenPeer userTokens = StoredUser.load(context);
//        if (userTokens != null)
//            registration = hub.register(userTokens.getAccessToken(), userTokens.getUsername());
//    }

//    public static Registration getRegistration() {
//        return registration;
//    }

}
