package com.example.homebudget.util;

import static org.junit.Assert.*;

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
}
