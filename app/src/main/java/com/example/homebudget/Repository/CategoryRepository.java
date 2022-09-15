package com.example.homebudget.Repository;

import com.example.homebudget.Model.Category;
import com.example.homebudget.Model.CategoryDao;

import java.util.List;

public class CategoryRepository {
    private final RoomRepository roomRepository;
    private final CategoryDao categoryDao;

    public CategoryRepository(){
        roomRepository = RoomRepository.getInstance();
        categoryDao = roomRepository.getCategoryDao();
    }

    public List<Category> getCategories(){
        return categoryDao.getCategories();
    }

    public void addCategory(Category category){
        categoryDao.addCategory(category);
    }

    public void deleteCategory(Category category){
        categoryDao.deleteCategory(category);
    }

    public void updateCategory(Category category){
        categoryDao.updateCategory(category);
    }
}
