package com.example.homebudget.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert
    public void addItem(Item item);

    @Update
    public void updateItem(Item item);

    @Delete
    public void deleteItem(Item item);

    @Query("select * from Item")
    public List<Item> getItems();

    @Query("delete from Item where category_id = :categoryId ")
    public void deleteItemsWithCategoryId(int categoryId);

    @Query("delete from Item where id = :ids")
    public void deletedSelectedItems(List<Integer> ids);
}
