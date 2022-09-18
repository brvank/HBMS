package com.example.homebudget.ViewModel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.homebudget.Model.Category;
import com.example.homebudget.Model.Item;
import com.example.homebudget.Model.Plan;
import com.example.homebudget.Repository.HomeRepository;
import com.example.homebudget.Repository.RoomDB;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private final HomeRepository homeRepository;

    private final MutableLiveData<List<Category>> categoryLiveData;
    private final MutableLiveData<List<Item>> itemLiveData;
    private final MutableLiveData<List<Plan>> planLiveData;

    public HomeViewModel(){
        homeRepository = new HomeRepository();

        categoryLiveData = new MutableLiveData<>(new ArrayList<>());
        itemLiveData = new MutableLiveData<>(new ArrayList<>());
        planLiveData = new MutableLiveData<>(new ArrayList<>());
    }

    public void categoryLiveDataObserve(LifecycleOwner lifecycleOwner, Observer<List<Category>> observer){
        categoryLiveData.observe(lifecycleOwner, observer);
    }

    public void itemLiveDataObserve(LifecycleOwner lifecycleOwner, Observer<List<Item>> observer){
        itemLiveData.observe(lifecycleOwner, observer);
    }

    public void planLiveDataObserve(LifecycleOwner lifecycleOwner, Observer<List<Plan>> observer){
        planLiveData.observe(lifecycleOwner, observer);
    }

    public void addCategory(Category category){
        List<Category> list = categoryLiveData.getValue();
        list.add(category);
        categoryLiveData.setValue(list);
    }

    public void removeCategory(int i){
        List<Category> list = getCategories();
        list.remove(i);
        categoryLiveData.setValue(list);
    }

    /**
     * <b>Remember to use this function in a non UI thread.<b1>
    */
    public String query(RoomDB.DIV d, RoomDB.QUERY q, Object obj){
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
        }

        List<Plan> plans = homeRepository.planRepository.getPlans();
        postPlans(plans);
    }

    //util functions for this view model
    public List<Category> getCategories(){
        return categoryLiveData.getValue();
    }

    private void setCategories(List<Category> categories){
        categoryLiveData.setValue(categories);
    }

    private void postCategories(List<Category> categories){
        categoryLiveData.postValue(categories);
    }

    public List<Item> getItems(){
        return itemLiveData.getValue();
    }

    public void setItems(List<Item> items){
        itemLiveData.setValue(items);
    }

    public void postItems(List<Item> items){
        itemLiveData.postValue(items);
    }

    public List<Plan> getPlans(){
        return planLiveData.getValue();
    }

    public void setPlans(List<Plan> plans){
        planLiveData.setValue(plans);
    }

    public void postPlans(List<Plan> plans){
        planLiveData.postValue(plans);
    }
}
