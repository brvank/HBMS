package com.example.homebudget.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.homebudget.R;
import com.example.homebudget.Service.Storage.SharedPreferencesStorage;
import com.example.homebudget.Util.AppAlert;
import com.example.homebudget.Util.AppConstant;
import com.example.homebudget.databinding.ActivityGetInBinding;

public class GetInActivity extends AppCompatActivity {

    ActivityGetInBinding activityGetInBinding;

    SharedPreferencesStorage sharedPreferencesStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGetInBinding = ActivityGetInBinding.inflate(getLayoutInflater());
        setContentView(activityGetInBinding.getRoot());

        try{
            initialise();
        }catch (Exception e){
            e.printStackTrace();
            AppAlert.toastLong(GetInActivity.this, GetInActivity.class + " " + e.getMessage());
        }
    }

    private void initialise(){
        sharedPreferencesStorage = new SharedPreferencesStorage(GetInActivity.this);

        setSupportActionBar(activityGetInBinding.tbGetIn);

        activityGetInBinding.btnGetIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = activityGetInBinding.etEnterName.getText().toString();
                if(name.isEmpty()){
                    AppAlert.toast(GetInActivity.this, "Please enter your name first!");
                }else{
                    getIn(name);
                }
            }
        });
    }

    private void getIn(String name){
        //removing "\r" and "\n" from name
        if(name.contains("\n")){
            name = name.replace("\n", "");
        }

        if(name.contains("\r")){
            name = name.replace("\r", "");
        }

        sharedPreferencesStorage.set(AppConstant.USER_NAME_SF, name);

        //onStartUp settings
        sharedPreferencesStorage.setBoolean(AppConstant.DASHBOARD_SHOW_SF, true);
        sharedPreferencesStorage.setBoolean(AppConstant.PLANS_SHOW_SF, false);

        Intent intent = new Intent(GetInActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
        enterTransition();
    }

    private void enterTransition(){
        overridePendingTransition(R.anim.slide_in_left, R.anim.no_anim);
    }

    private void exitTransition(){
        overridePendingTransition(R.anim.no_anim, R.anim.slide_out_right);
    }
}