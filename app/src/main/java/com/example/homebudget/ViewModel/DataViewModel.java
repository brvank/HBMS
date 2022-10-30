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
import com.example.homebudget.Repository.RoomDB;
import com.example.homebudget.Util.Callbacks.AppCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

abstract class DataViewModel extends ViewModel {
    protected final HomeRepository homeRepository;

    protected final MutableLiveData<List<Category>> categoryLiveData;
    protected final MutableLiveData<List<Item>> itemLiveData;
    protected final MutableLiveData<List<Plan>> planLiveData;
    protected final MutableLiveData<Boolean> errorOccurredLiveData;

    public DataViewModel(){
        homeRepository = new HomeRepository();

        categoryLiveData = new MutableLiveData<>(new ArrayList<>());
        itemLiveData = new MutableLiveData<>(new ArrayList<>());
        planLiveData = new MutableLiveData<>(new ArrayList<>());

        errorOccurredLiveData = new MutableLiveData<>(false);
    }

    public void addCategoryLiveDataObserver(LifecycleOwner lifecycleOwner, Observer<List<Category>> observer){
        categoryLiveData.observe(lifecycleOwner, observer);
    }

    public void addItemLiveDataObserver(LifecycleOwner lifecycleOwner, Observer<List<Item>> observer){
        itemLiveData.observe(lifecycleOwner, observer);
    }

    public void addPlanLiveDataObserver(LifecycleOwner lifecycleOwner, Observer<List<Plan>> observer){
        planLiveData.observe(lifecycleOwner, observer);
    }

    public void addErrorOccurredObserver(LifecycleOwner lifecycleOwner, Observer<Boolean> observer){
        errorOccurredLiveData.observe(lifecycleOwner, observer);
    }

    public void query(RoomDB.DIV d, RoomDB.QUERY q, Object obj){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String response = queryInBackground(d, q, obj);

                if(!response.isEmpty()){
                    errorOccurredLiveData.postValue(true);
                }
            }
        });
    }

    public void errorUpdate(Boolean status){
        errorOccurredLiveData.setValue(status);
    }

    /**
     * <b>Remember to use this function in a non UI thread.<b1>
     */
    private String queryInBackground(RoomDB.DIV d, RoomDB.QUERY q, Object obj){
        try{
            switch (d){
                case CATEGORY:
                    queryCategory(q, obj);
                    break;
                case ITEM:
                    queryItem(q, obj);
                    break;
                case PLANS:
                    queryPlans(q, obj);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }

        return "";
    }

    private void queryCategory(RoomDB.QUERY q, Object obj){
        switch (q){
            case ADD:
                homeRepository.categoryRepository.addCategory((Category) obj);
                break;
            case GET:
                break;
            case UPDATE:
                homeRepository.categoryRepository.updateCategory((Category) obj);
                break;
            case DELETE:
                homeRepository.categoryRepository.deleteCategory((Category) obj);
                break;
            case DELETE_SELECTED:
                homeRepository.categoryRepository.deleteSelectedCategories((List<Integer>) obj);
                break;
        }

        List<Category> categories = homeRepository.categoryRepository.getCategories();
        postCategories(categories);
    }

    private void queryItem(RoomDB.QUERY q, Object obj) {
        switch (q){
            case ADD:
                homeRepository.itemRepository.addItem((Item) obj);
                break;
            case GET:
                break;
            case UPDATE:
                homeRepository.itemRepository.updateItem((Item) obj);
                break;
            case DELETE:
                homeRepository.itemRepository.deleteItem((Item) obj);
                break;
            case DELETE_SELECTED:
                homeRepository.itemRepository.deleteSelectedItems((List<Integer>) obj);
                break;
            case DELETE_FOR_PARENT:
                homeRepository.itemRepository.deleteItemsWithCategoryId((int) obj);
                break;
        }

        List<Item> items = homeRepository.itemRepository.getItems();
        postItems(items);
    }

    private void queryPlans(RoomDB.QUERY q, Object obj) {
        switch (q){
            case ADD:
                homeRepository.planRepository.addPlan((Plan) obj);
                break;
            case GET:
                break;
            case UPDATE:
                homeRepository.planRepository.updatePlan((Plan) obj);
                break;
            case DELETE:
                homeRepository.planRepository.deletePlan((Plan) obj);
                break;
            case DELETE_SELECTED:
                homeRepository.planRepository.deleteSelectedPlans((List<Integer>) obj);
                break;
        }

        List<Plan> plans = homeRepository.planRepository.getPlans();
        postPlans(plans);
    }

    public List<Category> getCategories(){
        categoryLiveData.setValue(homeRepository.categoryRepository.getCategories());
        return categoryLiveData.getValue();
    }

    private void setCategories(List<Category> categories){
        categoryLiveData.setValue(categories);
    }

    private void postCategories(List<Category> categories){
        categoryLiveData.postValue(categories);
    }

    public List<Item> getItems(){
        itemLiveData.setValue(homeRepository.itemRepository.getItems());
        return itemLiveData.getValue();
    }

    public void setItems(List<Item> items){
        itemLiveData.setValue(items);
    }

    public void postItems(List<Item> items){
        itemLiveData.postValue(items);
    }

    public List<Plan> getPlans(){
        planLiveData.setValue(homeRepository.planRepository.getPlans());
        return planLiveData.getValue();
    }

    public void setPlans(List<Plan> plans){
        planLiveData.setValue(plans);
    }

    public void postPlans(List<Plan> plans){
        planLiveData.postValue(plans);
    }

    //specific queries
    public void validateCategory(int id, AppCallback success, AppCallback failure){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Category> categories = homeRepository.categoryRepository.getCategoryById(id);
                if(!categories.isEmpty()){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            success.callback();
                        }
                    });
                }else{
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            failure.callback();
                        }
                    });
                }
            }
        });
    }
}
