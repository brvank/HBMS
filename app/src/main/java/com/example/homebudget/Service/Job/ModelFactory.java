package com.example.homebudget.Service.Job;

import com.example.homebudget.Model.Category;
import com.example.homebudget.Model.Item;
import com.example.homebudget.Model.Plan;

import org.json.JSONException;
import org.json.JSONObject;

public class ModelFactory {
    public static final String id = "id",
            name = "name",
            info = "info",
            extra = "extra",
            dateOfCreation = "dateOfCreation",
            categoryId = "categoryId",
            current = "current",
            previous = "previous";

    public static String categoryToString(Category category) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(name, category.getName());
        jsonObject.put(info, category.getInfo());
        jsonObject.put(extra, category.getExtra());
        jsonObject.put(dateOfCreation, category.getDateOfCreation());

        return jsonObject.toString();
    }

    public static Category generateCategory(JSONObject jsonObject) throws JSONException {
        Category category = new Category(jsonObject.getString(name), jsonObject.getString(info), jsonObject.getString(dateOfCreation));
        category.setExtra(jsonObject.getString(extra));

        return category;
    }

    public static String itemToString(Item item) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(name, item.getName());
        jsonObject.put(info, item.getInfo());
        jsonObject.put(extra, item.getExtra());
        jsonObject.put(categoryId, item.getCategoryId());
        jsonObject.put(current, item.getCurrent());
        jsonObject.put(previous, item.getPrevious());

        return jsonObject.toString();
    }

    public static Item generateItem(JSONObject jsonObject) throws JSONException {
        Item item = new Item(jsonObject.getString(name), jsonObject.getString(info), jsonObject.getInt(current), jsonObject.getInt(previous), jsonObject.getInt(categoryId));
        item.setExtra(jsonObject.getString(extra));

        return item;
    }

    public static String planToString(Plan plan) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(name, plan.getName());
        jsonObject.put(extra, plan.getExtra());
        jsonObject.put(info, plan.getInfo());

        return jsonObject.toString();
    }

    public static Plan generatePlan(JSONObject jsonObject) throws JSONException {
        Plan plan = new Plan(jsonObject.getString(name), jsonObject.getString(info));
        plan.setExtra(jsonObject.getString(extra));

        return plan;
    }
}
