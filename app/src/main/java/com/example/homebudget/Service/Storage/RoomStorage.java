package com.example.homebudget.Service.Storage;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.homebudget.Model.Category;
import com.example.homebudget.Model.CategoryDao;
import com.example.homebudget.Model.Item;
import com.example.homebudget.Model.ItemDao;
import com.example.homebudget.Model.Plan;
import com.example.homebudget.Model.PlanDao;

@Database(entities = {Category.class, Item.class, Plan.class}, version = 1)
public abstract class RoomStorage extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract ItemDao itemDao();

    public abstract PlanDao planDao();
}
