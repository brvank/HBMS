package com.example.homebudget.View.FragmentView;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.homebudget.Model.Plan;
import com.example.homebudget.R;
import com.example.homebudget.Util.AppUtil;
import com.example.homebudget.Util.Callbacks.PlanCallback;
import com.example.homebudget.View.Adapter.PlanAdapter;
import com.example.homebudget.ViewModel.HomeActivityViewModel;
import com.example.homebudget.databinding.FragmentPlansBinding;

import java.util.List;

public class PlansFragment extends AppFragment {

    ActivityResultLauncher<Intent> planActivityResultLauncher;
    PlanCallback planCallback;

    FragmentPlansBinding fragmentPlansBinding;

    HomeActivityViewModel homeActivityViewModel;

    PlanAdapter planAdapter;

    public PlansFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentPlansBinding = FragmentPlansBinding.inflate(inflater, container, false);
        return fragmentPlansBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //instantiating home view model
        homeActivityViewModel = new ViewModelProvider(requireActivity()).get(HomeActivityViewModel.class);

        //instantiating adapter
        planActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK){
                    getPlans();
                }
            }
        });

        planCallback = new PlanCallback() {
            @Override
            public void callback(Plan plan) {
                //handle callback
            }
        };
        planAdapter = new PlanAdapter(homeActivityViewModel.getPlans(), planCallback);

        //setting adapter
        fragmentPlansBinding.rvPlan.setAdapter(planAdapter);

        //setting layout manager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(), AppUtil.screenInfo(requireActivity().getWindow()).recommendColumns());
        fragmentPlansBinding.rvPlan.setLayoutManager(gridLayoutManager);

        //setting up the swipe refresh layout
        fragmentPlansBinding.srlPlans.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fragmentPlansBinding.srlPlans.setRefreshing(false);
                getPlans();
            }
        });

        //adding observers
        addObservers();

        //getting plans
        getPlans();
    }

    private void getPlans(){
        homeActivityViewModel.queryPlansGet(new Runnable() {
            @Override
            public void run() {
                if(mounted()){
                    updateLoadingStatus(true);
                }
            }
        }, new Runnable() {
            @Override
            public void run() {
                if(mounted()){
                    updateLoadingStatus(false);
                }
            }
        }, new Runnable() {
            @Override
            public void run() {
                if(mounted()){
                    updateLoadingStatus(false);
                    showMessage(requireActivity(), "sfjio", "foewijf");
                }
            }
        });
    }

    private void addObservers(){
        homeActivityViewModel.addPlanLiveDataObserver(requireActivity(), new Observer<List<Plan>>() {
            @Override
            public void onChanged(List<Plan> plans) {
                planAdapter.setValues(plans);
                Integer position = plans.size() - 1;
                if(position < 0){
                    position = 0;
                }
                planAdapter.notifyDataSetChanged();
                fragmentPlansBinding.rvPlan.smoothScrollToPosition(position);
            }
        });
    }

    public void updateLoadingStatus(boolean status){
        fragmentPlansBinding.lpiPlans.setVisibility(AppUtil.visibility(status));
    }

    private void enterTransition(){
        requireActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.no_anim);
    }

    private void exitTransition(){
        requireActivity().overridePendingTransition(R.anim.no_anim, R.anim.slide_out_right);
    }
}