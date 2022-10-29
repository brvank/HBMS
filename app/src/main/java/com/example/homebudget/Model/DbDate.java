package com.example.homebudget.Model;

import androidx.annotation.NonNull;

import com.example.homebudget.Util.AppConstant;
import com.example.homebudget.Util.AppUtil;

public class DbDate {
    int year, month;

    public DbDate() {
        year = 0;
        month = 0;
    }

    public DbDate(int year, int month) {
        this.year = year;
        this.month = month;
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

    @NonNull
    @Override
    public String toString() {
        return AppUtil.month(getMonth()) + "," + getYear();
    }
}
