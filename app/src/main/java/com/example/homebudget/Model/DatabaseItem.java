package com.example.homebudget.Model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@Entity
public class DatabaseItem {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int categoryId, itemId;
    private String itemName, itemInfo, data;

    @Ignore
    private JSONObject jsonData;

    @Ignore
    public String YEAR = "years", dummyData = "{\"years\":{\"2022\":[93,99,105,111,117,123,129,135,141,147,153,159],\"2021\":[93,99,105,111,117,123,129,135,141,147,153,159]}}";

    public DatabaseItem(int categoryId, int itemId, String itemName, String itemInfo, String data) {
        this.categoryId = categoryId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemInfo = itemInfo;
        this.data = data;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(String itemInfo) {
        this.itemInfo = itemInfo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void updateJsonData(ArrayList<Integer> years, ArrayList<ArrayList<Integer>> itemsCostArray) throws JSONException {
        this.jsonData = createJsonData(years, itemsCostArray);
    }

    public void updateJsonDataItemPrice(String year, int monthIndex, Integer newValue) throws JSONException {
        jsonData.getJSONObject(YEAR).getJSONArray(year).put(monthIndex, newValue);
    }

    public JSONObject getJsonData(){
        return jsonData;
    }

    public void createJsonData() throws JSONException {
        this.jsonData = new JSONObject(dummyData);
    }

    private JSONObject createJsonData(ArrayList<Integer> years, ArrayList<ArrayList<Integer>> itemsCostArrayMonthly) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONObject yearObject = new JSONObject();

        for(int i=0;i<years.size();i++){
            ArrayList<Integer> itemCostArrayMonthly = itemsCostArrayMonthly.get(i);
            JSONArray costArray = new JSONArray();
            for(int j=0;j<itemCostArrayMonthly.size();j++){
                costArray.put(itemCostArrayMonthly.get(j));
            }
            yearObject.put(String.valueOf(years.get(i)), costArray);
        }

        jsonObject.put(YEAR, yearObject);

        return jsonObject;
    }
}
