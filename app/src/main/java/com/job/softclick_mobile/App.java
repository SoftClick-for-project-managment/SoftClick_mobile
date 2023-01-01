package com.job.softclick_mobile;

import android.app.Application;

public class App extends Application {
    private static App instance;

    public App() {
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }
}
