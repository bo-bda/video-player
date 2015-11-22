package com.android.bo.video;

import android.app.Application;
import android.content.Context;

/*
 * Created by Bo on 21.11.2015.
 */
public class BoApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}
