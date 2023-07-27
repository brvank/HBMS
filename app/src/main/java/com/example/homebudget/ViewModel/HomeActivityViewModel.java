package com.example.homebudget.ViewModel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.homebudget.Service.Storage.SharedPreferencesStorage;
import com.example.homebudget.Util.AppApplication;
import com.example.homebudget.Util.AppConstant;

public class HomeActivityViewModel extends DataViewModel {
    private final SharedPreferencesStorage sharedPreferencesStorage;

    private final MutableLiveData<Boolean> dashboardShowLiveData, planShowLiveData;
    private final MutableLiveData<String> userNameLiveData;

    public HomeActivityViewModel(){
        sharedPreferencesStorage = new SharedPreferencesStorage(AppApplication.getContext());

        dashboardShowLiveData = new MutableLiveData<>(dashboardSavedStatus());
        planShowLiveData = new MutableLiveData<>(planSavedStatus());
        userNameLiveData = new MutableLiveData<>(userName());

        setViewStates();
    }

    public void addDashboardShowObserver(LifecycleOwner lifecycleOwner, Observer<Boolean> observer){
        dashboardShowLiveData.observe(lifecycleOwner, observer);
    }

    public void addPlanShowObserver(LifecycleOwner lifecycleOwner, Observer<Boolean> observer){
        planShowLiveData.observe(lifecycleOwner, observer);
    }

    public void addUserNameObserver(LifecycleOwner lifecycleOwner, Observer<String> observer){
        userNameLiveData.observe(lifecycleOwner, observer);
    }

    private void setViewStates(){
        boolean d = dashboardSavedStatus();
        boolean p = planSavedStatus();

        if(!(d||p)){
            setDashboardShow(true);
            setPlanShow(false);
        }else{
            setDashboardShow(d);
            setPlanShow(p);
        }
    }

    //dashboard
    public Boolean getDashboardShow(){
        return dashboardShowLiveData.getValue();
    }

    public void setDashboardShow(Boolean d){
        sharedPreferencesStorage.setBoolean(AppConstant.DASHBOARD_SHOW_SF, d);
        dashboardShowLiveData.setValue(d);
    }

    private Boolean dashboardSavedStatus(){
        return sharedPreferencesStorage.getBoolean(AppConstant.DASHBOARD_SHOW_SF);
    }

    //plans
    public Boolean getPlansShow(){
        return planShowLiveData.getValue();
    }

    public void setPlanShow(Boolean p){
        sharedPreferencesStorage.setBoolean(AppConstant.PLANS_SHOW_SF, p);
        planShowLiveData.setValue(p);
    }

    private Boolean planSavedStatus(){
        return sharedPreferencesStorage.getBoolean(AppConstant.PLANS_SHOW_SF);
    }

    //username
    public String getUserName(){
        return userNameLiveData.getValue();
    }

    public void setUserName(String name){
        sharedPreferencesStorage.set(AppConstant.USER_NAME_SF, name);
        userNameLiveData.setValue(name);
    }

    private String userName(){
        return sharedPreferencesStorage.get(AppConstant.USER_NAME_SF);
    }

    //update
    public void update(){
        dashboardShowLiveData.setValue(dashboardSavedStatus());
        planShowLiveData.setValue(planSavedStatus());
        userNameLiveData.setValue(userName());
    }

    //logout
    public void logout(LifecycleOwner lifecycleOwner){
        sharedPreferencesStorage.clear();

        dashboardShowLiveData.removeObservers(lifecycleOwner);
        planShowLiveData.removeObservers(lifecycleOwner);
        userNameLiveData.removeObservers(lifecycleOwner);

        dashboardShowLiveData.setValue(true);
        planShowLiveData.setValue(true);
        userNameLiveData.setValue("");
    }
}
