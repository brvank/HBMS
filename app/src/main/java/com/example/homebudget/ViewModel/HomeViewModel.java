package com.example.homebudget.ViewModel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.homebudget.Service.Storage.SharedPreferencesStorage;
import com.example.homebudget.Util.AppApplication;
import com.example.homebudget.Util.AppConstant;

public class HomeViewModel extends DataViewModel {
    private final SharedPreferencesStorage sharedPreferencesStorage;

    private final MutableLiveData<Boolean> dashboardShowLiveData, planShowLiveData;
    private final MutableLiveData<String> userNameLiveData;

    public HomeViewModel(){
        sharedPreferencesStorage = new SharedPreferencesStorage(AppApplication.getContext());

        dashboardShowLiveData = new MutableLiveData<>(dashboardStatus());
        planShowLiveData = new MutableLiveData<>(planStatus());
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
        boolean d = dashboardStatus();
        boolean p = planStatus();

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

    private Boolean dashboardStatus(){
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

    private Boolean planStatus(){
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
        dashboardShowLiveData.setValue(dashboardStatus());
        planShowLiveData.setValue(planStatus());
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
