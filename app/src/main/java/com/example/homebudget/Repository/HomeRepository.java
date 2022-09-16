package com.example.homebudget.Repository;

public class HomeRepository {
    public final CategoryRepository categoryRepository;
    public final ItemRepository itemRepository;
    public final PlanRepository planRepository;

    public HomeRepository(){
        categoryRepository = new CategoryRepository();
        itemRepository = new ItemRepository();
        planRepository = new PlanRepository();
    }
}
