package com.example.homebudget.Repository;

import com.example.homebudget.Model.Plan;
import com.example.homebudget.Model.PlanDao;

import java.util.List;

public class PlanRepository {
    private final RoomRepository roomRepository;
    private final PlanDao planDao;

    public PlanRepository(){
        roomRepository = RoomRepository.getInstance();
        planDao = roomRepository.getPlanDao();
    }

    public List<Plan> getPlans(){
        return planDao.getPlans();
    }

    public void addPlan(Plan plan){
        planDao.addPlan(plan);
    }

    public void deletePlan(Plan plan){
        planDao.deletePlan(plan);
    }

    public void updatePlan(Plan plan){
        planDao.updatePlan(plan);
    }

    public void deleteSelectedPlans(List<Integer> ids){
        planDao.deleteSelectedPlans(ids);
    }
}
