package com.example.homebudget.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.homebudget.R;
import com.example.homebudget.Service.Storage.SharedPreferencesStorage;
import com.example.homebudget.Util.AppAlert;
import com.example.homebudget.Util.AppConstant;
import com.example.homebudget.Util.AppUtil;
import com.example.homebudget.View.FragmentView.DashboardFragment;
import com.example.homebudget.View.FragmentView.PlansFragment;
import com.example.homebudget.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding activityHomeBinding;

    SharedPreferencesStorage sharedPreferencesStorage;

    DashboardFragment dashboardFragment;
    PlansFragment plansFragment;

    ActivityResultLauncher<Intent> settingsActivityResultLauncher;

    boolean dashboardShow = false;
    boolean plansShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(activityHomeBinding.getRoot());

        try{
            initialise();
        }catch (Exception e){
            e.printStackTrace();
            AppAlert.toastLong(HomeActivity.this, HomeActivity.class + " " + e.getMessage());
        }
    }

    private void initialise(){
        sharedPreferencesStorage = new SharedPreferencesStorage(HomeActivity.this);

        setSupportActionBar(activityHomeBinding.tbDashboard);


        setupResultLaunchers();
        setupDashboard();
        setupNavigationDrawer();
    }

    private void setupResultLaunchers(){
        settingsActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK){
                    setViewStates();
                    toggleFragmentView();
                }
            }
        });
    }

    private void setupDashboard(){
        setViewStates();

        dashboardFragment = new DashboardFragment();
        plansFragment = new PlansFragment();

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                .add(activityHomeBinding.frgDashboard.getId(), dashboardFragment)
                .commit();

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                .add(activityHomeBinding.frgPlans.getId(), plansFragment)
                .commit();

        toggleFragmentView();
    }

    private void setViewStates(){
        dashboardShow = sharedPreferencesStorage.getBoolean(AppConstant.DASHBOARD_SHOW_SF);
        plansShow = sharedPreferencesStorage.getBoolean(AppConstant.PLANS_SHOW_SF);

        if(!(dashboardShow || plansShow)){
            dashboardShow = true;
            sharedPreferencesStorage.setBoolean(AppConstant.DASHBOARD_SHOW_SF, true);
        }
    }

    private void toggleFragmentView(){
        activityHomeBinding.frgDashboard.setVisibility(AppUtil.visibility(dashboardShow));
        activityHomeBinding.frgPlans.setVisibility(AppUtil.visibility(plansShow));
    }

    private void setupNavigationDrawer(){
        activityHomeBinding.nvDashboard.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                navigationMenuSelected(item.getItemId());
                return true;
            }
        });

        activityHomeBinding.ivHamburgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer();
            }
        });

        String name = sharedPreferencesStorage.get(AppConstant.USER_NAME_SF);

        if(name.isEmpty()){
            AppAlert.toastLong(HomeActivity.this, AppConstant.LOGGED_OUT);
            Intent intent = new Intent(HomeActivity.this, GetInActivity.class);
            startActivity(intent);
            finish();
            enterTransition();
        }else{
            View view = activityHomeBinding.nvDashboard.getHeaderView(0);
            TextView tvUserName = view.findViewById(R.id.tvUserName);
            tvUserName.setText(AppUtil.firstUpperCase(name));
        }
    }

    private void navigationMenuSelected(Integer id){
        if(id == R.id.mnDashboard){
            dashboard();
        } else if(id == R.id.mnPlans){
            plans();
        } else if(id == R.id.mnYourDatabase){
            userDatabase();
        } else if(id == R.id.mnSettings){
            settings();
        } else if(id == R.id.mnAbout){
            about();
        } else if(id == R.id.mnShare){
            share();
        } else if(id == R.id.mnLogout){
            logout();
        }
    }

    private void dashboard(){
        AppAlert.toast(HomeActivity.this, "Dashboard function");
    }

    private void plans(){
        AppAlert.toast(HomeActivity.this, "Plans function");
    }

    private void userDatabase(){
        AppAlert.toast(HomeActivity.this, "Your database function");
    }

    private void settings(){
        Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);

        settingsActivityResultLauncher.launch(intent);
        enterTransition();
    }

    private void about(){
        AppAlert.toast(HomeActivity.this, "About function");
    }

    private void logout(){
        AppAlert.toast(HomeActivity.this, "Logout function");
    }

    private void share(){
        AppAlert.toast(HomeActivity.this, "Share function");
    }

    private void closeDrawer(){
        activityHomeBinding.dlDashboard.closeDrawer(GravityCompat.START);
    }

    private void openDrawer(){
        activityHomeBinding.dlDashboard.openDrawer(GravityCompat.START);
    }

    private void enterTransition(){
        overridePendingTransition(R.anim.slide_in_left, R.anim.no_anim);
    }

    private void exitTransition(){
        overridePendingTransition(R.anim.no_anim, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        if(activityHomeBinding.dlDashboard.isDrawerOpen(GravityCompat.START)){
            activityHomeBinding.dlDashboard.closeDrawer(GravityCompat.START);
        }else{
            AppAlert.toast(HomeActivity.this, "HBMS closed");
            super.onBackPressed();
        }
    }
}