package com.joern.guesswhat.application;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();
    }

    // get app context everywhere
    public static Context getAppContext() {
        return App.context;
    }

}