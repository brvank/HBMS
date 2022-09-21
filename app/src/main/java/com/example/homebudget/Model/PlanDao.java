package com.example.homebudget.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlanDao {
    @Insert
    public void addPlan(Plan plan);

    @Update
    public void updatePlan(Plan plan);

    @Delete
    public void deletePlan(Plan plan);

    @Query("select * from `Plan`")
    public List<Plan> getPlans();

    @Query("delete from `Plan` where id = :ids")
    public void deleteSelectedPlans(List<Integer> ids);
}
