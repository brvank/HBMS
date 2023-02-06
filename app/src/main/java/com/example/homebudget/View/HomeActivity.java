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
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.homebudget.Model.Category;
import com.example.homebudget.Model.Selection;
import com.example.homebudget.R;
import com.example.homebudget.Service.Job.AsyncTaskParameter;
import com.example.homebudget.Util.AppAlert;
import com.example.homebudget.Util.AppConstant;
import com.example.homebudget.Util.AppUtil;
import com.example.homebudget.View.Dialog.CategoryDialog;
import com.example.homebudget.View.Dialog.AddSelectDialog;
import com.example.homebudget.View.FragmentView.DashboardFragment;
import com.example.homebudget.View.FragmentView.PlansFragment;
import com.example.homebudget.ViewModel.HomeActivityViewModel;
import com.example.homebudget.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppActivity {

    ActivityHomeBinding activityHomeBinding;

    ActivityResultLauncher<Intent> settingsActivityResultLauncher;

    DashboardFragment dashboardFragment;
    PlansFragment plansFragment;

    HomeActivityViewModel homeActivityViewModel;

    Bundle savedInstanceState;
    Integer selectedIndex = 0;

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

        setupViewModel();
        setupResultLauncher();
        setupDashboard();
        setupNavigationDrawer();
    }

    private void setupViewModel(){
        //for view state view model
        homeActivityViewModel = new ViewModelProvider(HomeActivity.this).get(HomeActivityViewModel.class);

        homeActivityViewModel.addDashboardShowObserver(HomeActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                activityHomeBinding.frgDashboard.setVisibility(AppUtil.visibility(b));
            }
        });

        homeActivityViewModel.addPlanShowObserver(HomeActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                activityHomeBinding.frgPlans.setVisibility(AppUtil.visibility(b));
            }
        });

        homeActivityViewModel.addUserNameObserver(HomeActivity.this, new Observer<String>() {
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

        //for home view model
        homeActivityViewModel = new ViewModelProvider(HomeActivity.this).get(HomeActivityViewModel.class);
    }

    private void setupResultLauncher(){
        settingsActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK){
                    homeActivityViewModel.update();
                }
            }
        });
    }

    private void setupDashboard(){
        if(savedInstanceState == null){
            dashboardFragment = new DashboardFragment();
            plansFragment = new PlansFragment();

            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                    .replace(activityHomeBinding.frgDashboard.getId(), dashboardFragment)
                    .commit();

            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                    .replace(activityHomeBinding.frgPlans.getId(), plansFragment)
                    .commit();
        }

        //update fragment visibility
        updateFragmentVisibility();
    }

    private void updateFragmentVisibility(){
        activityHomeBinding.frgDashboard.setVisibility(AppUtil.visibility(homeActivityViewModel.getDashboardShow()));
        activityHomeBinding.frgPlans.setVisibility(AppUtil.visibility(homeActivityViewModel.getPlansShow()));
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

        String name = homeActivityViewModel.getUserName();

        if(name.isEmpty()){
            appLogout();
        }else{
            View view = activityHomeBinding.nvDashboard.getHeaderView(0);
            TextView tvUserName = view.findViewById(R.id.tvUserName);
            tvUserName.setText(AppUtil.firstUpperCase(name));
        }
    }

    private void navigationMenuSelected(Integer id){
        if(id == R.id.mnDrawerDashboard){
            dashboard();
        } else if(id == R.id.mnDrawerPlans){
            plans();
        } else if(id == R.id.mnDrawerYourDatabase){
            userDatabase();
        } else if(id == R.id.mnDrawerSettings){
            settings();
        } else if(id == R.id.mnDrawerAbout){
            about();
        } else if(id == R.id.mnDrawerShare){
            share();
        } else if(id == R.id.mnDrawerLogout){
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
        homeActivityViewModel.logout(HomeActivity.this);
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
        boolean dashboardShow = homeActivityViewModel.getDashboardShow();
        boolean plansShow = homeActivityViewModel.getPlansShow();

        if(dashboardShow && plansShow){
            //show dialog to choose one
            AddSelectDialog addSelectDialog = new AddSelectDialog(HomeActivity.this, false, selectedIndex, new AddSelectDialog.SelectedResultCallback() {
                @Override
                public void selected(Selection.Selected selected, int si) {
                    selectedIndex = si;
                    switch(selected){
                        case CATEGORY:
                            addCategory();
                            break;
                        case PLAN:
                            addPlan();
                            break;
                    }
                }
            });
            addSelectDialog.show(getSupportFragmentManager(), AppConstant.SELECTION_DIALOG_TAG);
        }else if(dashboardShow){
            addCategory();
        }else{
            addPlan();
        }
    }

    private void addCategory(){
        AsyncTaskParameter.Builder builder = new AsyncTaskParameter.Builder();
        builder.setOnPreExecute(new Runnable() {
            @Override
            public void run() {
                if(mounted()){
                    dashboardFragment.updateLoadingStatus(true);
                }
            }
        });
        builder.setOnSuccess(new Runnable() {
            @Override
            public void run() {
                if(mounted()){
                    dashboardFragment.updateLoadingStatus(false);
                }
            }
        });
        builder.setOnError(new Runnable() {
            @Override
            public void run() {
                if(mounted()){
                    dashboardFragment.updateLoadingStatus(false);
                }
            }
        });

        CategoryDialog categoryDialog = new CategoryDialog(HomeActivity.this, new CategoryDialog.CategoryDialogCallback() {
            @Override
            public void callback(Category category) {
                homeActivityViewModel.queryCategoryAdd(category, builder.build());
            }
        });
        categoryDialog.show(getSupportFragmentManager(), AppConstant.CATEGORY_DIALOG_TAG);
    }

    private void addPlan(){
        plansFragment.updateLoadingStatus(true);

        //todo: add logic for showing the dialog and adding the plan
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
            case R.id.mnHomeAdd:
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setLoading(boolean status) {

    }
}