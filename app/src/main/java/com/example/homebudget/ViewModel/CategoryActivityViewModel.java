package com.example.homebudget.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CategoryActivityViewModel extends ViewModel {
    public MutableLiveData<Boolean> loading;

    public CategoryActivityViewModel(){
        loading = new MutableLiveData<>(true);
    }
}
