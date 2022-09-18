package com.example.homebudget.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.homebudget.Model.Selection;
import com.example.homebudget.R;
import com.example.homebudget.Service.Storage.SharedPreferencesStorage;
import com.example.homebudget.Util.AppAlert;
import com.example.homebudget.Util.AppConstant;
import com.example.homebudget.Util.AppUtil;
import com.example.homebudget.View.Dialog.AddDialogFragment;
import com.example.homebudget.View.FragmentView.DashboardFragment;
import com.example.homebudget.View.FragmentView.PlansFragment;
import com.example.homebudget.ViewModel.ViewStateViewModel;
import com.example.homebudget.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding activityHomeBinding;

    ActivityResultLauncher<Intent> settingsActivityResultLauncher;

    DashboardFragment dashboardFragment;
    PlansFragment plansFragment;

    ViewStateViewModel viewStateViewModel;

    Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(activityHomeBinding.getRoot());

        this.savedInstanceState = savedInstanceState;

        try{
            initialise();
        }catch (Exception e){
            e.printStackTrace();
            AppAlert.toastLong(HomeActivity.this, HomeActivity.class + " " + e.getMessage());
        }
    }

    private void initialise(){
        setSupportActionBar(activityHomeBinding.tbDashboard);

        setupViewStateViewModel();
        setupResultLauncher();
        setupDashboard();
        setupNavigationDrawer();
    }

    private void setupViewStateViewModel(){
        viewStateViewModel = new ViewModelProvider(HomeActivity.this).get(ViewStateViewModel.class);

        viewStateViewModel.addDashboardShowObserver(HomeActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                activityHomeBinding.frgDashboard.setVisibility(AppUtil.visibility(b));
            }
        });

        viewStateViewModel.addPlanShowObserver(HomeActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                activityHomeBinding.frgDashboard.setVisibility(AppUtil.visibility(b));
            }
        });
    }

    private void setupResultLauncher(){
        settingsActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK){
                    viewStateViewModel.update();
                    updateFragmentVisibility();
                }
            }
        });
    }

    private void setupDashboard(){
        if(savedInstanceState == null){
            dashboardFragment = new DashboardFragment();
            plansFragment = new PlansFragment();

            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                    .add(activityHomeBinding.frgDashboard.getId(), dashboardFragment)
                    .commit();

            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                    .add(activityHomeBinding.frgPlans.getId(), plansFragment)
                    .commit();
        }


        //update fragment visibility
        updateFragmentVisibility();

        //adding observers
        viewStateViewModel.addDashboardShowObserver(HomeActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                activityHomeBinding.frgDashboard.setVisibility(AppUtil.visibility(b));
            }
        });

        viewStateViewModel.addPlanShowObserver(HomeActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                activityHomeBinding.frgPlans.setVisibility(AppUtil.visibility(b));
            }
        });

        viewStateViewModel.addUserNameObserver(HomeActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String name) {
                if(name.isEmpty()){
                    appLogout();
                }else{
                    View view = activityHomeBinding.nvDashboard.getHeaderView(0);
                    TextView tvUserName = view.findViewById(R.id.tvUserName);
                    tvUserName.setText(AppUtil.firstUpperCase(name));
                }
            }
        });
    }

    private void updateFragmentVisibility(){
        activityHomeBinding.frgDashboard.setVisibility(AppUtil.visibility(viewStateViewModel.getDashboardShow()));
        activityHomeBinding.frgPlans.setVisibility(AppUtil.visibility(viewStateViewModel.getPlansShow()));
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

        String name = viewStateViewModel.getUserName();

        if(name.isEmpty()){
            appLogout();
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
        closeDrawer();
        appLogout();
    }

    private void appLogout(){
        viewStateViewModel.logout(HomeActivity.this);
        AppAlert.toast(HomeActivity.this, AppConstant.LOGGED_OUT);
        Intent intent = new Intent(HomeActivity.this, SplashActivity.class);
        startActivity(intent);
        finish();
        enterTransition();
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

    private void add(){
        boolean dashboardShow = viewStateViewModel.getDashboardShow();
        boolean plansShow = viewStateViewModel.getPlansShow();

        if(dashboardShow && plansShow){
            //show dialog to choose one
            AddDialogFragment addDialogFragment = new AddDialogFragment(new AddDialogFragment.SelectedResultCallback() {
                @Override
                public void selected(Selection.Selected selected) {

                }
            });
            addDialogFragment.show(getSupportFragmentManager(), "ADD");
        }else if(dashboardShow){
            //direct to add dashboard dialog

        }else{
            //direct to add plans dialog

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnAdd:
                add();
                break;
        }
        return super.onOptionsItemSelected(item);
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