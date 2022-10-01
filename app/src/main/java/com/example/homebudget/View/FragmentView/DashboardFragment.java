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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.homebudget.Model.Category;
import com.example.homebudget.R;
import com.example.homebudget.Util.AppAlert;
import com.example.homebudget.Util.AppConstant;
import com.example.homebudget.Util.AppLog;
import com.example.homebudget.Util.Callbacks.CategoryCallback;
import com.example.homebudget.Util.Callbacks.UpdateCallback;
import com.example.homebudget.Util.AppUtil;
import com.example.homebudget.View.Adapter.CategoryAdapter;
import com.example.homebudget.View.CategoryActivity;
import com.example.homebudget.View.HomeActivity;
import com.example.homebudget.ViewModel.HomeViewModel;
import com.example.homebudget.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    ActivityResultLauncher<Intent> categoryActivityResultLauncher;
    CategoryCallback categoryCallback;

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
        categoryActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK){
                    updateLoadingStatus(true);
                    homeViewModel.getCategories();
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
        categoryAdapter = new CategoryAdapter(requireActivity(), homeViewModel.getCategories(), categoryCallback);

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
            ((HomeActivity)requireActivity()).setProcessCallbackFrgDashboard(new UpdateCallback() {
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
                categoryAdapter.notifyDataSetChanged();
                fragmentDashboardBinding.rvCategory.smoothScrollToPosition(position);
                updateLoadingStatus(false);
            }
        });
    }

    private void updateLoadingStatus(boolean status){
        fragmentDashboardBinding.lpiDashboard.setVisibility(AppUtil.visibility(status));
    }

    private void enterTransition(){
        requireActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.no_anim);
    }

    private void exitTransition(){
        requireActivity().overridePendingTransition(R.anim.no_anim, R.anim.slide_out_right);
    }
}