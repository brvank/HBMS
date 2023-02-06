package com.example.homebudget.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"name"}, unique = true)})
public class Item {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String name, info, extra;

    private Integer current, previous;

    @ColumnInfo(name = "category_id")
    private Integer categoryId;

    public Item(String name, String info, Integer current, Integer previous, Integer categoryId){
        this.name = name;
        this.info = info;
        this.current = current;
        this.previous = previous;
        this.categoryId = categoryId;
        this.extra = "";
    }

    public static enum FetchOption{
        FETCH_ALL,
        FETCH_BY_ID,
        FETCH_NONE
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getPrevious() {
        return previous;
    }

    public void setPrevious(Integer previous) {
        this.previous = previous;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
