package com.example.homebudget.Util;

import android.app.Application;
import android.content.Context;

public class AppApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        AppApplication.context = getApplicationContext();
    }

    public static Context getContext(){
        return AppApplication.context;
    }
}
