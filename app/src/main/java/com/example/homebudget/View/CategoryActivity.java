package com.example.homebudget.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.homebudget.Model.Item;
import com.example.homebudget.R;
import com.example.homebudget.Util.AppAlert;
import com.example.homebudget.Util.AppConstant;
import com.example.homebudget.Util.AppLog;
import com.example.homebudget.Util.AppUtil;
import com.example.homebudget.View.Adapter.ItemAdapter;
import com.example.homebudget.View.Dialog.ConfirmationDialog;
import com.example.homebudget.View.Dialog.ItemDialog;
import com.example.homebudget.ViewModel.CategoryActivityViewModel;
import com.example.homebudget.ViewModel.HomeActivityViewModel;
import com.example.homebudget.databinding.ActivityCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppActivity {

    HomeActivityViewModel homeActivityViewModel;
    CategoryActivityViewModel categoryActivityViewModel;
    ActivityCategoryBinding activityCategoryBinding;
    List<Item> itemList;
    ItemAdapter itemAdapter;

    //category fields
    String name, type;
    Integer id;

    Boolean isLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCategoryBinding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(activityCategoryBinding.getRoot());

        try{
            initialise();
        }catch (Exception e){
            e.printStackTrace();
            AppAlert.toastLong(CategoryActivity.this, CategoryActivity.class + " " + e.getMessage());
        }
    }

    private void initialise(){
        Intent intent = getIntent();
        name = intent.getStringExtra(AppConstant.NAME);
        id = intent.getIntExtra(AppConstant.ID, 0);
        type = intent.getStringExtra(AppConstant.TYPE);

        if(type.equalsIgnoreCase(AppConstant.CATEGORY)){
            setUpActionBar();
            setUpViews();
            addObservers();
        }else{
            closeActivity();
        }
    }

    private void setUpActionBar(){
        activityCategoryBinding.tbCategory.setTitle(name);
        setSupportActionBar(activityCategoryBinding.tbCategory);
    }

    private void setUpViews(){
        homeActivityViewModel = new ViewModelProvider(CategoryActivity.this).get(HomeActivityViewModel.class);
        categoryActivityViewModel = new ViewModelProvider(CategoryActivity.this).get(CategoryActivityViewModel.class);

        activityCategoryBinding.ivBackCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeActivity();
            }
        });

        itemAdapter = new ItemAdapter(CategoryActivity.this, new ArrayList<>());

        activityCategoryBinding.rvItem.setAdapter(itemAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(CategoryActivity.this, AppUtil.screenInfo(getWindow()).recommendColumns(AppConstant.CARD_WIDTH * 2));
        activityCategoryBinding.rvItem.setLayoutManager(gridLayoutManager);

        activityCategoryBinding.srlItem.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                activityCategoryBinding.srlItem.setRefreshing(false);

                Runnable onPreExecute = () -> {
                    if(mounted()){
                        updateLoadingStatus(true);
                    }
                };

                Runnable onSuccess = () -> {
                    if(mounted()){
                        updateLoadingStatus(false);
                    }
                };

                Runnable onError = () -> {
                    if(mounted()){
                        updateLoadingStatus(false);
                        showMessage(CategoryActivity.this, AppConstant.OOPS, AppConstant.TRY_LATER);
                    }
                };
                AppLog.d(String.valueOf(id));
                homeActivityViewModel.queryItemsGet(id, onPreExecute, onSuccess, onError);
            }
        });
    }

    private void addObservers(){
        categoryActivityViewModel.loading.observe(CategoryActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean status) {
                isLoading = status;
                activityCategoryBinding.lpiCategoryPage.setVisibility(AppUtil.visibility(status));
                invalidateOptionsMenu();
            }
        });

        //adding item observer
        homeActivityViewModel.addItemLiveDataObserver(CategoryActivity.this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                itemList = items;
                itemAdapter.setValues(items);
                Integer position = items.size() - 1;
                if(position < 0){
                    position = 0;
                }
                itemAdapter.notifyDataSetChanged();
                activityCategoryBinding.rvItem.smoothScrollToPosition(position);
                updateLoadingStatus(false);
            }
        });
    }

    private void updateLoadingStatus(Boolean status){
        categoryActivityViewModel.loading.setValue(status);
    }

    private void deleteCategory(){
        ConfirmationDialog confirmationDialog = new ConfirmationDialog(
                CategoryActivity.this,
                AppUtil.firstUpperCase(AppConstant.DELETE),
                AppConstant.CATEGORY_DELETE_CONFIRMATION,
                new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<Integer> ids = new ArrayList<>();
                        ids.add(id);
                        homeActivityViewModel.queryItemsDeleteByCategoryId(id, new Runnable() {
                            @Override
                            public void run() {
                                updateLoadingStatus(true);
                            }
                        }, new Runnable() {
                            @Override
                            public void run() {
                                updateLoadingStatus(false);
                                homeActivityViewModel.queryCategoryDeleteSelected(ids, new Runnable() {
                                    @Override
                                    public void run() {
                                        updateLoadingStatus(true);
                                    }
                                }, new Runnable() {
                                    @Override
                                    public void run() {
                                        updateLoadingStatus(false);
                                        closeActivity();
                                    }
                                }, new Runnable() {
                                    @Override
                                    public void run() {
                                        updateLoadingStatus(false);
                                        showMessage(CategoryActivity.this, AppConstant.OOPS, AppConstant.TRY_LATER);
                                    }
                                });
                            }
                        }, new Runnable() {
                            @Override
                            public void run() {
                                updateLoadingStatus(false);
                                showMessage(CategoryActivity.this, AppConstant.OOPS, AppConstant.TRY_LATER);
                            }
                        });
                    }
        });

        confirmationDialog.show(getSupportFragmentManager(), AppConstant.CATEGORY_DIALOG_TAG);
    }

    private void addItem(){

        ItemDialog itemDialog = new ItemDialog(CategoryActivity.this, itemList, new ItemDialog.ItemDialogCallback() {
            @Override
            public void callback(Item item) {
                item.setCategoryId(id);
                homeActivityViewModel.queryItemAdd(item, new Runnable() {
                    @Override
                    public void run() {
                        updateLoadingStatus(true);
                    }
                }, new Runnable() {
                    @Override
                    public void run() {
                        updateLoadingStatus(false);
                    }
                }, new Runnable() {
                    @Override
                    public void run() {
                        updateLoadingStatus(false);
                        showMessage(CategoryActivity.this, AppConstant.OOPS, AppConstant.TRY_LATER);
                    }
                });
            }
        });

        itemDialog.show(getSupportFragmentManager(), AppConstant.ITEM_DIALOG_TAG);
    }

    private void closeActivity(){
        setResult(RESULT_OK);
        finish();
        exitTransition();
    }

    private void enterTransition(){
        overridePendingTransition(R.anim.slide_in_left, R.anim.no_anim);
    }

    private void exitTransition(){
        overridePendingTransition(R.anim.no_anim, R.anim.slide_out_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.category_screen_menu, menu);
        for(int i = 0; i<menu.size(); i++){
            menu.getItem(i).setVisible(!isLoading);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.mnCategoryAdd:
                addItem();
                break;
            case R.id.mnCategoryDelete:
                deleteCategory();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }

    @Override
    public void setLoading(boolean status) {

    }
}