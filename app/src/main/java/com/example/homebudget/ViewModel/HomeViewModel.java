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

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final HomeRepository homeRepository;

    private final MutableLiveData<List<Category>> categoryLiveData;
    private final MutableLiveData<List<Item>> itemLiveData;
    private final MutableLiveData<List<Plan>> planLiveData;


    public HomeViewModel(){
        homeRepository = new HomeRepository();

        categoryLiveData = new MutableLiveData<>();
        itemLiveData = new MutableLiveData<>();
        planLiveData = new MutableLiveData<>();
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
        categoryLiveData.postValue(categories);
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
        itemLiveData.postValue(items);
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
        planLiveData.postValue(plans);
    }
}
