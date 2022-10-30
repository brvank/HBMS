package com.example.homebudget.Repository;

public class RoomDB {
    public enum QUERY{
        GET,
        GET_BY_ID,
        ADD,
        DELETE,
        UPDATE,
        DELETE_SELECTED,
        DELETE_FOR_PARENT
    }

    public enum DIV{
        CATEGORY,
        ITEM,
        PLANS
    }
}
