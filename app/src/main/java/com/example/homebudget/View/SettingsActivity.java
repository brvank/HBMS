package com.example.homebudget.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.example.homebudget.R;
import com.example.homebudget.Service.Storage.SharedPreferencesStorage;
import com.example.homebudget.Util.AppAlert;
import com.example.homebudget.Util.AppCallback;
import com.example.homebudget.Util.AppConstant;
import com.example.homebudget.Util.AppLog;
import com.example.homebudget.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding activitySettingsBinding;

    SharedPreferencesStorage sharedPreferencesStorage;

    boolean dashboardShow;
    boolean plansShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySettingsBinding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(activitySettingsBinding.getRoot());

        try{
            initialise();
        }catch (Exception e){
            e.printStackTrace();
            AppAlert.toastLong(SettingsActivity.this, SettingsActivity.class + " " + e.getMessage());
        }

    }

    private void initialise(){
        sharedPreferencesStorage = new SharedPreferencesStorage(SettingsActivity.this);

        activitySettingsBinding.ivBackSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeActivity();
            }
        });

        setupSettings();
    }

    private void setupSettings(){
        //setting up titles
        activitySettingsBinding.rbBothShow.setText(this.getResources().getString(R.string.both_dashboard_plan_screens));
        activitySettingsBinding.rbDashboardShow.setText(this.getResources().getString(R.string.dashboard_screen));
        activitySettingsBinding.rbPlansShow.setText(this.getResources().getString(R.string.plans_screen));

        //getting saved values from sf
        getViewStates();

        //toggle view states
        toggleViewsState();


        //adding callbacks
        activitySettingsBinding.rgShowOnStartup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                boolean both = activitySettingsBinding.rbBothShow.isChecked();
                boolean dashboard = activitySettingsBinding.rbDashboardShow.isChecked();
                boolean plans = activitySettingsBinding.rbPlansShow.isChecked();
                if(both){
                    dashboardShow = true;
                    plansShow = true;
                }else{
                    dashboardShow = dashboard;
                    plansShow = plans;
                }
                setViewStates();
            }
        });

    }

    private void getViewStates(){
        dashboardShow = sharedPreferencesStorage.getBoolean(AppConstant.DASHBOARD_SHOW_SF);
        plansShow = sharedPreferencesStorage.getBoolean(AppConstant.PLANS_SHOW_SF);

        if(!(dashboardShow || plansShow)){
            dashboardShow = true;
            sharedPreferencesStorage.setBoolean(AppConstant.DASHBOARD_SHOW_SF, true);
        }
    }

    private void setViewStates(){
        if(!(dashboardShow || plansShow)){
            dashboardShow = true;
        }
        sharedPreferencesStorage.setBoolean(AppConstant.DASHBOARD_SHOW_SF, dashboardShow);
        sharedPreferencesStorage.setBoolean(AppConstant.PLANS_SHOW_SF, plansShow);
    }

    private void toggleViewsState(){
        if(dashboardShow && plansShow){
            activitySettingsBinding.rbBothShow.setChecked(true);
        }else{
            activitySettingsBinding.rbDashboardShow.setChecked(dashboardShow);
            activitySettingsBinding.rbPlansShow.setChecked(plansShow);
        }
    }

    private void closeActivity(){
        setResult(RESULT_OK);
        finish();
        exitTransition();
    }

    private void enterTransition(){
        overridePendingTransition(R.anim.slide_in_left, R.anim.no_anim);
    }

    private void exitTransition(){
        overridePendingTransition(R.anim.no_anim, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exitTransition();
    }
}