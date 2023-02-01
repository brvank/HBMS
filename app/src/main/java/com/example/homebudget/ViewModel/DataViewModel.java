package com.example.homebudget.ViewModel;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.homebudget.Model.Category;
import com.example.homebudget.Model.Item;
import com.example.homebudget.Model.Plan;
import com.example.homebudget.Repository.HomeRepository;
import com.example.homebudget.Service.Job.AsyncTaskExecution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

abstract class DataViewModel extends ViewModel {
    protected final HomeRepository homeRepository;

    protected final MutableLiveData<List<Category>> categoryLiveData;
    protected final MutableLiveData<List<Item>> itemLiveData;
    protected final MutableLiveData<List<Plan>> planLiveData;

    public DataViewModel(){
        homeRepository = new HomeRepository();

        categoryLiveData = new MutableLiveData<>(new ArrayList<>());
        itemLiveData = new MutableLiveData<>(new ArrayList<>());
        planLiveData = new MutableLiveData<>(new ArrayList<>());
    }

    //for adding the observers
    public void addCategoryLiveDataObserver(LifecycleOwner lifecycleOwner, Observer<List<Category>> observer){
        categoryLiveData.observe(lifecycleOwner, observer);
    }

    public void addItemLiveDataObserver(LifecycleOwner lifecycleOwner, Observer<List<Item>> observer){
        itemLiveData.observe(lifecycleOwner, observer);
    }

    public void addPlanLiveDataObserver(LifecycleOwner lifecycleOwner, Observer<List<Plan>> observer){
        planLiveData.observe(lifecycleOwner, observer);
    }

    //new queries
    public void query(Runnable onPreExecute, Runnable doInBackground, Runnable onSuccess, Runnable onError){
        new AsyncTaskExecution(onPreExecute, doInBackground, onSuccess, onError).execute();
    }

    //delayed query
    public void query(Runnable onPreExecute, Runnable doInBackground, Runnable onSuccess, Runnable onError, Long time){
        new AsyncTaskExecution(onPreExecute, doInBackground, onSuccess, onError).execute(time);
    }

    //queries for category
    //get categories
    public void queryCategoryGet(Runnable onPreExecute, Runnable onSuccess, Runnable onError){
        query(onPreExecute, new Runnable() {
            @Override
            public void run() {
                categoryLiveData.postValue(homeRepository.categoryRepository.getCategories());
            }
        }, onSuccess, onError);
    }

    //add category
    public void queryCategoryAdd(Category category, Runnable onPreExecute, Runnable onSuccess, Runnable onError){
        query(onPreExecute, new Runnable() {
            @Override
            public void run() {
                homeRepository.categoryRepository.addCategory(category);
                categoryLiveData.postValue(homeRepository.categoryRepository.getCategories());
            }
        }, onSuccess, onError);
    }

    //update category
    public void queryCategoryUpdate(Category category, Runnable onPreExecute, Runnable onSuccess, Runnable onError){
        query(onPreExecute, new Runnable() {
            @Override
            public void run() {
                homeRepository.categoryRepository.updateCategory(category);
                categoryLiveData.postValue(homeRepository.categoryRepository.getCategories());
            }
        }, onSuccess, onError);
    }

    //delete category
    public void queryCategoryDelete(Category category, Runnable onPreExecute, Runnable onSuccess, Runnable onError){
        query(onPreExecute, new Runnable() {
            @Override
            public void run() {
                homeRepository.categoryRepository.deleteCategory(category);
                categoryLiveData.postValue(homeRepository.categoryRepository.getCategories());
            }
        }, onSuccess, onError);
    }

    //delete categories
    public void queryCategoryDeleteSelected(List<Integer> ids, Runnable onPreExecute, Runnable onSuccess, Runnable onError){
        query(onPreExecute, new Runnable() {
            @Override
            public void run() {
                homeRepository.categoryRepository.deleteSelectedCategories(ids);
                categoryLiveData.postValue(homeRepository.categoryRepository.getCategories());
            }
        }, onSuccess, onError);
    }

    //validate category(specific query)
    public void queryValidateCategory(int id, Runnable onPreExecute, Runnable onSuccess, Runnable onFailure){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                if(onPreExecute != null){
                    handler.post(onPreExecute);
                }
                itemLiveData.postValue(new ArrayList<>());
                List<Category> categories = homeRepository.categoryRepository.getCategoryById(id);
                if(!categories.isEmpty()){
                    //fetching the list of items associated with the category
                    itemLiveData.postValue(homeRepository.itemRepository.getItemsByCategoryId(id));
                    if(onSuccess != null) handler.post(onSuccess);
                }else{
                    if(onFailure != null) handler.post(onFailure);
                }
            }
        });
    }

    //queries for item
    //get items
    public void queryItemsGet(Runnable onPreExecute, Runnable onSuccess, Runnable onError){
        query(onPreExecute, new Runnable() {
            @Override
            public void run() {
                itemLiveData.postValue(homeRepository.itemRepository.getItems());
            }
        }, onSuccess, onError);
    }

    //specific queries
    public void queryItemsGet(int id, Runnable onPreExecute, Runnable onSuccess, Runnable onError){
        query(onPreExecute, new Runnable() {
            @Override
            public void run() {
                itemLiveData.postValue(homeRepository.itemRepository.getItemsByCategoryId(id));
            }
        }, onSuccess, onError);
    }

    //add item
    public void queryItemAdd(Item item, Runnable onPreExecute, Runnable onSuccess, Runnable onError){
        query(onPreExecute, new Runnable() {
            @Override
            public void run() {
                homeRepository.itemRepository.addItem(item);
                itemLiveData.postValue(homeRepository.itemRepository.getItems());
            }
        }, onSuccess, onError);
    }

    //update item
    public void queryItemUpdate(Item item, Runnable onPreExecute, Runnable onSuccess, Runnable onError){
        query(onPreExecute, new Runnable() {
            @Override
            public void run() {
                homeRepository.itemRepository.updateItem(item);
                itemLiveData.postValue(homeRepository.itemRepository.getItems());
            }
        }, onSuccess, onError);
    }

    //delete item
    public void queryItemDelete(Item item, Runnable onPreExecute, Runnable onSuccess, Runnable onError){
        query(onPreExecute, new Runnable() {
            @Override
            public void run() {
                homeRepository.itemRepository.deleteItem(item);
                itemLiveData.postValue(homeRepository.itemRepository.getItems());
            }
        }, onSuccess, onError);
    }

    //delete items(category id)
    public void queryItemsDeleteByCategoryId(Integer id, Runnable onPreExecute, Runnable onSuccess, Runnable onError){
        query(onPreExecute, new Runnable() {
            @Override
            public void run() {
                homeRepository.itemRepository.deleteItemsWithCategoryId(id);
                itemLiveData.postValue(homeRepository.itemRepository.getItems());
            }
        }, onSuccess, onError);
    }

    //delete items
    public void queryItemDeleteSelected(List<Integer> ids, Runnable onPreExecute, Runnable onSuccess, Runnable onError){
        query(onPreExecute, new Runnable() {
            @Override
            public void run() {
                homeRepository.itemRepository.deleteSelectedItems(ids);
                itemLiveData.postValue(homeRepository.itemRepository.getItems());
            }
        }, onSuccess, onError);
    }

    //queries for plan
    //get plans
    public void queryPlansGet(Runnable onPreExecute, Runnable onSuccess, Runnable onError){
        query(onPreExecute, new Runnable() {
            @Override
            public void run() {
                planLiveData.postValue(homeRepository.planRepository.getPlans());
            }
        }, onSuccess, onError);
    }

    //add plan
    public void queryPlanAdd(Plan plan, Runnable onPreExecute, Runnable onSuccess, Runnable onError){
        query(onPreExecute, new Runnable() {
            @Override
            public void run() {
                homeRepository.planRepository.addPlan(plan);
                planLiveData.postValue(homeRepository.planRepository.getPlans());
            }
        }, onSuccess, onError);
    }

    //update plan
    public void queryPlanUpdate(Plan plan, Runnable onPreExecute, Runnable onSuccess, Runnable onError){
        query(onPreExecute, new Runnable() {
            @Override
            public void run() {
                homeRepository.planRepository.updatePlan(plan);
                planLiveData.postValue(homeRepository.planRepository.getPlans());
            }
        }, onSuccess, onError);
    }

    //delete plan
    public void queryPlanDelete(Plan plan, Runnable onPreExecute, Runnable onSuccess, Runnable onError){
        query(onPreExecute, new Runnable() {
            @Override
            public void run() {
                homeRepository.planRepository.deletePlan(plan);
                planLiveData.postValue(homeRepository.planRepository.getPlans());
            }
        }, onSuccess, onError);
    }

    //delete plans
    public void queryPlanDeleteSelected(List<Integer> ids, Runnable onPreExecute, Runnable onSuccess, Runnable onError){
        query(onPreExecute, new Runnable() {
            @Override
            public void run() {
                homeRepository.planRepository.deleteSelectedPlans(ids);
                planLiveData.postValue(homeRepository.planRepository.getPlans());
            }
        }, onSuccess, onError);
    }

    public List<Category> getCategories(){
        return categoryLiveData.getValue();
    }

    public List<Item> getItems(){
        return itemLiveData.getValue();
    }

    public List<Plan> getPlans(){
        return planLiveData.getValue();
    }
}
