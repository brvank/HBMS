package com.example.homebudget.ViewModel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.homebudget.Model.Plan;

import java.util.ArrayList;

public class CategoryActivityViewModel extends DataViewModel {
    public MutableLiveData<Boolean> loading;
    public MutableLiveData<Boolean> planDialogShow;
    public MutableLiveData<Plan> planMutableLiveData;

    public CategoryActivityViewModel(){
        loading = new MutableLiveData<>(false);
        planDialogShow = new MutableLiveData<>(false);
        planMutableLiveData = new MutableLiveData<>(new Plan("", ""));
    }


}
