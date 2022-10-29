package com.example.homebudget.Util;

import android.content.res.Configuration;
import android.view.View;
import android.view.Window;

import com.example.homebudget.Model.DbDate;
import com.example.homebudget.Model.ScreenInfo;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AppUtil {
    //conversion
    public static String month(int i){
        String month = "";
        if(i==1){
            month = AppConstant.MONTHS.JANUARY;
        }else if(i==2){
            month = AppConstant.MONTHS.FEBRUARY;
        }else if(i==3){
            month = AppConstant.MONTHS.MARCH;
        }else if(i==4){
            month = AppConstant.MONTHS.APRIL;
        }else if(i==5){
            month = AppConstant.MONTHS.MAY;
        }else if(i==6){
            month = AppConstant.MONTHS.JUNE;
        }else if(i==7){
            month = AppConstant.MONTHS.JULY;
        }else if(i==8){
            month = AppConstant.MONTHS.AUGUST;
        }else if(i==9){
            month = AppConstant.MONTHS.SEPTEMBER;
        }else if(i==10){
            month = AppConstant.MONTHS.OCTOBER;
        }else if(i==11){
            month = AppConstant.MONTHS.NOVEMBER;
        }else if(i==12){
            month = AppConstant.MONTHS.DECEMBER;
        }
        return month;
    }

    public static String firstUpperCase(String str){
        try{
            return str.substring(0, 1).toUpperCase() +
                    str.substring(1).toLowerCase();
        }catch (Exception e){
            e.printStackTrace();
            return str;
        }
    }

    //ui
    public static int visibility(boolean state){
        if(state){
            return View.VISIBLE;
        }else{
            return View.GONE;
        }
    }

    public static ScreenInfo screenInfo(Window window){
        Configuration configuration = window.getWindowStyle().getResources().getConfiguration();
        int width = configuration.screenWidthDp;
        int height = configuration.screenHeightDp;

        return new ScreenInfo(width, height);
    }

    public static String todayDate(){
        StringBuilder stringBuilder = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        stringBuilder.append(calendar.get(Calendar.YEAR)).append(AppConstant.DATE_SEPARATOR).append(calendar.get(Calendar.MONTH) + 1);
        return stringBuilder.toString();
    }

    public static DbDate dbDateUtil(String dbDateStr){
        DbDate dbDate = new DbDate();
        String[] date = dbDateStr.split(AppConstant.DATE_SEPARATOR);
        dbDate.setYear(Integer.parseInt(date[0]));
        dbDate.setMonth(Integer.parseInt(date[1]));
        return dbDate;
    }

    public static void testPrint(String tag, char decoration, int steps){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<steps;i++){
            stringBuilder.append(decoration);
        }

        stringBuilder.append(tag);

        for(int i=0;i<steps;i++){
            stringBuilder.append(decoration);
        }

        System.out.println(stringBuilder);
    }
}
