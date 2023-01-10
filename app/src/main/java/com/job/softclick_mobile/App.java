package com.job.softclick_mobile;

import android.app.Application;

import com.job.softclick_mobile.utils.PushNotificationHub;

public class App extends Application {
    private static App instance;

    public App() {
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        PushNotificationHub.init(this);
    }
}
