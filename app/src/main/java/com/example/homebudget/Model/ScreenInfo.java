package com.example.homebudget.Model;

import com.example.homebudget.Util.AppConstant;

public class ScreenInfo {
    int width;
    int height;

    public ScreenInfo(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    private int columns(int w){
        int count = width/w;
        if(count <= 0){
            count = 1;
        }

        return count;
    }

    public int recommendColumns(){
        return columns(AppConstant.CARD_WIDTH);
    }

    public int recommendColumns(int requiredWidth){
        return columns(requiredWidth);
    }
}
