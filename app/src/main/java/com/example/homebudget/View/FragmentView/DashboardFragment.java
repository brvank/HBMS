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

import com.example.homebudget.Model.Category;
import com.example.homebudget.R;
import com.example.homebudget.Util.AppConstant;
import com.example.homebudget.Util.Callbacks.CategoryCallback;
import com.example.homebudget.Util.AppUtil;
import com.example.homebudget.View.Adapter.CategoryAdapter;
import com.example.homebudget.View.CategoryActivity;
import com.example.homebudget.ViewModel.HomeActivityViewModel;
import com.example.homebudget.databinding.FragmentDashboardBinding;

import java.util.List;

public class DashboardFragment extends AppFragment {

    ActivityResultLauncher<Intent> categoryActivityResultLauncher;
    CategoryCallback categoryCallback;

    FragmentDashboardBinding fragmentDashboardBinding;

    HomeActivityViewModel homeActivityViewModel;

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
        homeActivityViewModel = new ViewModelProvider(requireActivity()).get(HomeActivityViewModel.class);

        //instantiating adapter
        categoryActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK){
                    getCategories();
                }
            }
        });

        categoryCallback = new CategoryCallback() {
            @Override
            public void callback(Category category) {
                Intent intent = new Intent(requireActivity(), CategoryActivity.class);
                intent.putExtra(AppConstant.NAME, category.getName());
                intent.putExtra(AppConstant.ID, category.getId());
                intent.putExtra(AppConstant.TYPE, AppConstant.CATEGORY);

                categoryActivityResultLauncher.launch(intent);
                enterTransition();
            }
        };
        categoryAdapter = new CategoryAdapter(homeActivityViewModel.getCategories(), categoryCallback);

        //setting adapter
        fragmentDashboardBinding.rvCategory.setAdapter(categoryAdapter);

        //setting layout manager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(), AppUtil.screenInfo(requireActivity().getWindow()).recommendColumns());
        fragmentDashboardBinding.rvCategory.setLayoutManager(gridLayoutManager);

        //setting up the swipe refresh layout
        fragmentDashboardBinding.srlDashboard.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fragmentDashboardBinding.srlDashboard.setRefreshing(false);
                getCategories();
            }
        });

        //adding observers
        addObservers();

        //getting categories
        getCategories();
    }

    private void getCategories(){
        homeActivityViewModel.queryCategoryGet(new Runnable() {
            @Override
            public void run() {
                if(mounted()) {
                    updateLoadingStatus(true);
                }
            }
        }, new Runnable() {
            @Override
            public void run() {
                if(mounted()) {
                    updateLoadingStatus(false);
                }
            }
        }, new Runnable() {
            @Override
            public void run() {
                if(mounted()){
                    updateLoadingStatus(false);
                    showMessage(getActivity(), "Error", "Something went wrong!\nPlease retry!");
                }
            }
        });
    }

    private void addObservers(){
        homeActivityViewModel.addCategoryLiveDataObserver(requireActivity(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryAdapter.setValues(categories);
                Integer position = categories.size() - 1;
                if(position < 0){
                    position = 0;
                }
                categoryAdapter.notifyDataSetChanged();
                fragmentDashboardBinding.rvCategory.smoothScrollToPosition(position);
            }
        });
    }

    public void updateLoadingStatus(boolean status){
        fragmentDashboardBinding.lpiDashboard.setVisibility(AppUtil.visibility(status));
    }

    private void enterTransition(){
        requireActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.no_anim);
    }

    private void exitTransition(){
        requireActivity().overridePendingTransition(R.anim.no_anim, R.anim.slide_out_right);
    }
}