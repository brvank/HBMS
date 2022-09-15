package com.example.homebudget.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.homebudget.R;
import com.example.homebudget.Service.Storage.SharedPreferencesStorage;
import com.example.homebudget.Util.AppAlert;
import com.example.homebudget.Util.AppConstant;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferencesStorage sharedPreferencesStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try{
            initialise();
        }catch (Exception e){
            e.printStackTrace();
            AppAlert.toastLong(SplashActivity.this, SplashActivity.class + " " + e.getMessage());
        }
    }

    private void initialise(){
        sharedPreferencesStorage = new SharedPreferencesStorage(SplashActivity.this);

        nextScreenCheck();
    }

    private void nextScreenCheck(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String name = sharedPreferencesStorage.get(AppConstant.USER_NAME_SF);

                if(name.isEmpty()){
                    Intent intent = new Intent(SplashActivity.this, GetInActivity.class);
                    startActivity(intent);
                    finish();
                    enterTransition();
                }else{
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    enterTransition();
                }
            }
        }, 100);
    }

    private void enterTransition(){
        overridePendingTransition(R.anim.slide_in_left, R.anim.no_anim);
    }
}