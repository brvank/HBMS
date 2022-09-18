package com.example.homebudget.Service.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.homebudget.Util.AppConstant;

public class SharedPreferencesStorage {
    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public SharedPreferencesStorage(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(AppConstant.SF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String get(String field){
        return sharedPreferences.getString(field, "");
    }

    public void set(String field, String value){
        editor.putString(field, value);
        editor.apply();
    }

    public boolean getBoolean(String field){
        return sharedPreferences.getBoolean(field, false);
    }

    public void setBoolean(String field, boolean value){
        editor.putBoolean(field, value);
        editor.apply();
    }

    public void clear(){
        editor.clear();
        editor.commit();
    }
}
