package com.example.homebudget.Repository;

import android.content.Context;

import androidx.room.Room;

import com.example.homebudget.Model.CategoryDao;
import com.example.homebudget.Model.ItemDao;
import com.example.homebudget.Model.PlanDao;
import com.example.homebudget.Service.Storage.RoomStorage;
import com.example.homebudget.Util.AppApplication;
import com.example.homebudget.Util.AppConstant;

class RoomRepository {
    private static RoomRepository roomRepository;
    private RoomStorage roomStorage;
    private CategoryDao categoryDao;
    private ItemDao itemDao;
    private PlanDao planDao;

    private RoomRepository(){}

    synchronized public static RoomRepository getInstance(){
        if(roomRepository == null){
            roomRepository = new RoomRepository();
        }
        return roomRepository;
    }

    synchronized private RoomStorage getRoomStorage(){
        if(roomStorage == null){
            roomStorage = Room.databaseBuilder(AppApplication.getContext(), RoomStorage.class, AppConstant.APP_RD)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return roomStorage;
    }
    
    synchronized public CategoryDao getCategoryDao(){
        if(categoryDao == null){
            categoryDao = getRoomStorage().categoryDao();
        }
        return categoryDao;
    }

    synchronized public ItemDao getItemDao(){
        if(itemDao == null){
            itemDao = getRoomStorage().itemDao();
        }
        return itemDao;
    }

    synchronized public PlanDao getPlanDao(){
        if(planDao == null){
            planDao = getRoomStorage().planDao();
        }
        return planDao;
    }
}
