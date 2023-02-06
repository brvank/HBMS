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
import com.example.homebudget.Service.Job.AsyncTaskParameter;

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
    public void query(AsyncTaskParameter asyncTaskParameter){
        new AsyncTaskExecution(asyncTaskParameter).execute();
    }

    //delayed query
    public void query(AsyncTaskParameter asyncTaskParameter, Long time){
        new AsyncTaskExecution(asyncTaskParameter).execute(time);
    }

    //queries for category
    //get categories
    public void queryCategoryGet(AsyncTaskParameter asyncTaskParameter){
        asyncTaskParameter.setDoInBackground(new Runnable() {
            @Override
            public void run() {
                categoryLiveData.postValue(homeRepository.categoryRepository.getCategories());
            }
        });
        query(asyncTaskParameter);
    }

    //add category
    public void queryCategoryAdd(Category category, AsyncTaskParameter asyncTaskParameter){
        asyncTaskParameter.setDoInBackground(new Runnable() {
            @Override
            public void run() {
                homeRepository.categoryRepository.addCategory(category);
                categoryLiveData.postValue(homeRepository.categoryRepository.getCategories());
            }
        });
        query(asyncTaskParameter);
    }

    //update category
    public void queryCategoryUpdate(Category category, AsyncTaskParameter asyncTaskParameter){
        asyncTaskParameter.setDoInBackground(new Runnable() {
            @Override
            public void run() {
                homeRepository.categoryRepository.updateCategory(category);
                categoryLiveData.postValue(homeRepository.categoryRepository.getCategories());
            }
        });
        query(asyncTaskParameter);
    }

    //delete category
    public void queryCategoryDelete(Category category, AsyncTaskParameter asyncTaskParameter){
        asyncTaskParameter.setDoInBackground(new Runnable() {
            @Override
            public void run() {
                homeRepository.categoryRepository.deleteCategory(category);
                categoryLiveData.postValue(homeRepository.categoryRepository.getCategories());
            }
        });
        query(asyncTaskParameter);
    }

    //delete categories
    public void queryCategoryDeleteSelected(List<Integer> ids, AsyncTaskParameter asyncTaskParameter){
        asyncTaskParameter.setDoInBackground(new Runnable() {
            @Override
            public void run() {
                homeRepository.categoryRepository.deleteSelectedCategories(ids);
                categoryLiveData.postValue(homeRepository.categoryRepository.getCategories());
            }
        });
        query(asyncTaskParameter);
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
    public void queryItemsGet(AsyncTaskParameter asyncTaskParameter){
        asyncTaskParameter.setDoInBackground(new Runnable() {
            @Override
            public void run() {
                itemLiveData.postValue(homeRepository.itemRepository.getItems());
            }
        });
        query(asyncTaskParameter);
    }

    //specific queries
    public void queryItemsGet(int id, AsyncTaskParameter asyncTaskParameter){
        asyncTaskParameter.setDoInBackground(new Runnable() {
            @Override
            public void run() {
                itemLiveData.postValue(homeRepository.itemRepository.getItemsByCategoryId(id));
            }
        });
        query(asyncTaskParameter);
    }

    //add item
    public void queryItemAdd(Item item, AsyncTaskParameter asyncTaskParameter, Item.FetchOption fetchOption){
        asyncTaskParameter.setDoInBackground(new Runnable() {
            @Override
            public void run() {
                homeRepository.itemRepository.addItem(item);
                fetchItem(fetchOption, item);
            }
        });
        query(asyncTaskParameter);
    }

    //update item
    public void queryItemUpdate(Item item, AsyncTaskParameter asyncTaskParameter, Item.FetchOption fetchOption){
        asyncTaskParameter.setDoInBackground(new Runnable() {
            @Override
            public void run() {
                homeRepository.itemRepository.updateItem(item);
                fetchItem(fetchOption, item);
            }
        });
        query(asyncTaskParameter);
    }

    //delete item
    public void queryItemDelete(Item item, AsyncTaskParameter asyncTaskParameter, Item.FetchOption fetchOption){
        asyncTaskParameter.setDoInBackground(new Runnable() {
            @Override
            public void run() {
                homeRepository.itemRepository.deleteItem(item);
                fetchItem(fetchOption, item);
            }
        });
        query(asyncTaskParameter);
    }

    //delete items(category id)
    public void queryItemsDeleteByCategoryId(Integer id, AsyncTaskParameter asyncTaskParameter){
        asyncTaskParameter.setDoInBackground(new Runnable() {
            @Override
            public void run() {
                homeRepository.itemRepository.deleteItemsWithCategoryId(id);
            }
        });
        query(asyncTaskParameter);
    }

    //delete items
    public void queryItemDeleteSelected(List<Integer> ids, AsyncTaskParameter asyncTaskParameter){
        asyncTaskParameter.setDoInBackground(new Runnable() {
            @Override
            public void run() {
                homeRepository.itemRepository.deleteSelectedItems(ids);
            }
        });
        query(asyncTaskParameter);
    }

    //fetch function for item
    public void fetchItem(Item.FetchOption fetchOption, Item item){
        switch (fetchOption){
            case FETCH_ALL:
                itemLiveData.postValue(homeRepository.itemRepository.getItems());
                break;
            case FETCH_BY_ID:
                itemLiveData.postValue(homeRepository.itemRepository.getItemsByCategoryId(item.getCategoryId()));
                break;
            case FETCH_NONE:
        }
    }

    //queries for plan
    //get plans
    public void queryPlansGet(AsyncTaskParameter asyncTaskParameter){
        asyncTaskParameter.setDoInBackground(new Runnable() {
            @Override
            public void run() {
                planLiveData.postValue(homeRepository.planRepository.getPlans());
            }
        });
        query(asyncTaskParameter);
    }

    //add plan
    public void queryPlanAdd(Plan plan, AsyncTaskParameter asyncTaskParameter){
        asyncTaskParameter.setDoInBackground(new Runnable() {
            @Override
            public void run() {
                homeRepository.planRepository.addPlan(plan);
                planLiveData.postValue(homeRepository.planRepository.getPlans());
            }
        });
        query(asyncTaskParameter);
    }

    //update plan
    public void queryPlanUpdate(Plan plan, AsyncTaskParameter asyncTaskParameter){
        asyncTaskParameter.setDoInBackground(new Runnable() {
            @Override
            public void run() {
                homeRepository.planRepository.updatePlan(plan);
                planLiveData.postValue(homeRepository.planRepository.getPlans());
            }
        });
        query(asyncTaskParameter);
    }

    //delete plan
    public void queryPlanDelete(Plan plan, AsyncTaskParameter asyncTaskParameter){
        asyncTaskParameter.setDoInBackground(new Runnable() {
            @Override
            public void run() {
                homeRepository.planRepository.deletePlan(plan);
                planLiveData.postValue(homeRepository.planRepository.getPlans());
            }
        });
        query(asyncTaskParameter);
    }

    //delete plans
    public void queryPlanDeleteSelected(List<Integer> ids, AsyncTaskParameter asyncTaskParameter){
        asyncTaskParameter.setDoInBackground(new Runnable() {
            @Override
            public void run() {
                homeRepository.planRepository.deleteSelectedPlans(ids);
                planLiveData.postValue(homeRepository.planRepository.getPlans());
            }
        });
        query(asyncTaskParameter);
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
