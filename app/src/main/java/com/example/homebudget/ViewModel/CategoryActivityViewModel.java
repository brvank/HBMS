package com.example.homebudget.ViewModel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class CategoryActivityViewModel extends DataViewModel {
    public MutableLiveData<Boolean> loading;

    public CategoryActivityViewModel(){
        loading = new MutableLiveData<>(false);
    }
}
