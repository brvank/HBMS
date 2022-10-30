package com.example.homebudget.util;

import static org.junit.Assert.*;

import com.example.homebudget.Model.DbDate;
import com.example.homebudget.Util.AppUtil;

import org.junit.Test;

public class UtilTest {
    @Test
    public void monthName(){
        assertEquals(AppUtil.month(1), "january");
    }

    @Test
    public void upperCaseMonthName(){
        assertEquals(AppUtil.firstUpperCase(AppUtil.month(1)), "January");
    }

    @Test
    public void dbDateUtil(){
        String date = AppUtil.todayDate();
        DbDate dbDate = AppUtil.dbDateUtil(date);
        System.out.println(dbDate);
        System.out.println(date);
        assertEquals(dbDate,dbDate);
    }

    @Test
    public void todayDate(){
        assertNull(AppUtil.todayDate());
    }

    @Test
    public void strToInt(){
        Integer integer = 1;
        assertEquals(integer, AppUtil.strToInt("1"));
    }


}
