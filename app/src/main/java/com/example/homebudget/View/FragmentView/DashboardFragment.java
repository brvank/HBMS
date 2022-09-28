package com.example.homebudget.View.FragmentView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.homebudget.Model.Category;
import com.example.homebudget.Util.AppLog;
import com.example.homebudget.Util.Callbacks.AppCallback;
import com.example.homebudget.Util.AppUtil;
import com.example.homebudget.View.Adapter.CategoryAdapter;
import com.example.homebudget.View.HomeActivity;
import com.example.homebudget.ViewModel.HomeViewModel;
import com.example.homebudget.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    FragmentDashboardBinding fragmentDashboardBinding;

    HomeViewModel homeViewModel;

    CategoryAdapter categoryAdapter;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentDashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false);
        return fragmentDashboardBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //instantiating home view model
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        //instantiating adapter
        categoryAdapter = new CategoryAdapter(homeViewModel.getCategories());

        //setting adapter
        fragmentDashboardBinding.rvCategory.setAdapter(categoryAdapter);

        //setting layout manager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(), AppUtil.screenInfo(requireActivity().getWindow()).recommendColumns());
        fragmentDashboardBinding.rvCategory.setLayoutManager(gridLayoutManager);

        //adding callbacks
        addCallbacks();

        //adding observers
        addObservers();

        //setting initial view state
        updateLoadingStatus(false);
    }

    private void addCallbacks(){
        Class<? extends FragmentActivity> activity = requireActivity().getClass();

        if(activity == HomeActivity.class){
            ((HomeActivity)requireActivity()).setProcessCallbackFrgDashboard(new AppCallback() {
                @Override
                public void update(boolean status) {
                    updateLoadingStatus(status);
                }
            });
        }
    }

    private void addObservers(){
        homeViewModel.addCategoryLiveDataObserver(requireActivity(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryAdapter.setValues(categories);
                int position = categories.size() - 1;
                if(position < 0){
                    position = 0;
                }
                fragmentDashboardBinding.rvCategory.smoothScrollToPosition(position);
                updateLoadingStatus(false);
            }
        });
    }

    private void updateLoadingStatus(boolean status){
        fragmentDashboardBinding.lpiDashboard.setVisibility(AppUtil.visibility(status));
    }
}