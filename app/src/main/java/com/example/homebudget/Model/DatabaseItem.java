package com.example.homebudget.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DatabaseItem {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int categoryId, itemId, year, month, current, previous;
    private String itemName, extra;

    public DatabaseItem(int categoryId, int itemId, int year, int month, int current, int previous, String itemName) {
        this.categoryId = categoryId;
        this.itemId = itemId;
        this.year = year;
        this.month = month;
        this.current = current;
        this.previous = previous;
        this.itemName = itemName;
        this.extra = "";
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPrevious() {
        return previous;
    }

    public void setPrevious(int previous) {
        this.previous = previous;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
