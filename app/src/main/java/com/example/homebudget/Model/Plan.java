package com.example.homebudget.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Plan {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String info;

    public Plan(String name, String info){
        this.name = name;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
