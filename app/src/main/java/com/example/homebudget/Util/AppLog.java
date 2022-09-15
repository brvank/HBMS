package com.example.homebudget.Util;

import android.util.Log;

public class AppLog {
    public static void d(String log){
        try{
            Log.d(AppConstant.LOG_D, log);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void e(String log){
        try{
            Log.e(AppConstant.LOG_E, log);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
