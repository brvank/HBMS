package com.example.homebudget.model;

import com.example.homebudget.Model.Category;
import com.example.homebudget.Service.Job.ModelFactory;
import com.example.homebudget.Util.AppUtil;

import org.json.JSONException;
import org.junit.Test;

public class ModelTest {

    @Test
    public void categoryTest() throws JSONException {
        Category category = new Category("category", "info", AppUtil.todayDate());
        System.out.println(category.toString());
        System.out.println(ModelFactory.categoryToString(category));
    }
}
