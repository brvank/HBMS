package com.example.homebudget.Model;

public class Selection{
    Selected selected;

    public Selection(){
        selected = Selected.CATEGORY;
    }

    public void setSelected(Selected selected){
        this.selected = selected;
    }

    public Selected getSelected(){
        return selected;
    }

    public enum Selected {
        CATEGORY,
        ITEM,
        PLAN
    }
}
