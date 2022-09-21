package com.example.homebudget.services;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.homebudget.Model.Category;
import com.example.homebudget.Model.CategoryDao;
import com.example.homebudget.Service.Storage.RoomStorage;
import com.example.homebudget.Util.AppApplication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class RoomDbTest {
    private CategoryDao categoryDao;
    private RoomStorage roomStorage;

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        roomStorage = Room.inMemoryDatabaseBuilder(context, RoomStorage.class).build();
        categoryDao = roomStorage.categoryDao();
    }

    @Test
    public void testDb(){
        Category category = new Category("category", "info", "22/01/2002");
        categoryDao.addCategory(category);
        List<Category> categoryList = categoryDao.getCategories();
        Log.d("category -> created", category.toString());
        Log.d("category -> fetched", categoryList.get(0).toString());
        assertEquals(categoryList.get(0), category);
    }

    @After
    public void closeDb(){
        roomStorage.close();
    }
}
