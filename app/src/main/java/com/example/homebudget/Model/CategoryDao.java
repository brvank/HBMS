package com.example.homebudget.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    public void addCategory(Category category);

    @Update
    public void updateCategory(Category category);

    @Delete
    public void deleteCategory(Category category);

    @Query("select * from Category")
    public List<Category> getCategories();

    @Query("select * from Category where id = :id")
    public List<Category> getCategoryById(int id);

    @Query("delete from Category where id = :ids")
    public void deleteSelectedCategories(List<Integer> ids);
}
