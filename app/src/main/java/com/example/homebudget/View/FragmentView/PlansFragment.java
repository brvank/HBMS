package com.example.homebudget.View.FragmentView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.homebudget.Util.AppUtil;
import com.example.homebudget.View.Adapter.PlanAdapter;
import com.example.homebudget.ViewModel.HomeViewModel;
import com.example.homebudget.databinding.FragmentPlansBinding;

public class PlansFragment extends Fragment {

    FragmentPlansBinding fragmentPlansBinding;

    HomeViewModel homeViewModel;

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
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        //instantiating adapter
        planAdapter = new PlanAdapter(homeViewModel.getPlans());

        //setting adapter
        fragmentPlansBinding.rvPlan.setAdapter(planAdapter);

        //setting layout manager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(), AppUtil.screenInfo(requireActivity().getWindow()).recommendColumns());
        fragmentPlansBinding.rvPlan.setLayoutManager(gridLayoutManager);
    }
}