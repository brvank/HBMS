package com.example.homebudget.jsonDatabase;

import static org.junit.Assert.assertEquals;

import com.example.homebudget.Model.DatabaseItem;
import com.example.homebudget.Util.AppUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class JsonTest {
    @Test
    public void jsonTest() throws Exception {
        ArrayList<Integer> yearsList = new ArrayList<>();
        yearsList.add(2021);
        yearsList.add(2022);

        ArrayList<ArrayList<Integer>> itemsCostArrayMonthly = new ArrayList<>();
        for(Integer i=0;i<yearsList.size();i++){
            itemsCostArrayMonthly.add(new ArrayList<>());
            for(Integer j=0;j<12;j++){
                itemsCostArrayMonthly.get(i).add((j+13)*21 - 15*(j+12));
            }
        }

        DatabaseItem databaseItem = new DatabaseItem(0, 0, "dummy", "info", "data");
        databaseItem.createJsonData();

        System.out.println(databaseItem.getJsonData());
        System.out.println(databaseItem.getJsonData().toString());

        databaseItem.updateJsonData(yearsList, itemsCostArrayMonthly);

        System.out.println(databaseItem.getJsonData());

        databaseItem.updateJsonDataItemPrice("2022", 3, 10);

        System.out.println(databaseItem.getJsonData());

        assertEquals("", "");
    }
}
