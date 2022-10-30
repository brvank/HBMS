package com.example.homebudget.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.homebudget.Model.Item;
import com.example.homebudget.R;
import com.example.homebudget.Repository.RoomDB;
import com.example.homebudget.Util.AppAlert;
import com.example.homebudget.Util.AppConstant;
import com.example.homebudget.Util.AppLog;
import com.example.homebudget.Util.AppUtil;
import com.example.homebudget.Util.Callbacks.AppCallback;
import com.example.homebudget.View.Dialog.ConfirmationDialog;
import com.example.homebudget.View.Dialog.ItemDialog;
import com.example.homebudget.ViewModel.HomeViewModel;
import com.example.homebudget.databinding.ActivityCategoryBinding;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    HomeViewModel homeViewModel;
    ActivityCategoryBinding activityCategoryBinding;
    boolean visible = false;

    //category fields
    String name, type;
    int id;

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
            validateCategory();
        }else{
            closeActivity();
        }
    }

    private void validateCategory(){
        activityCategoryBinding.lpiCategoryPage.setVisibility(View.VISIBLE);
        homeViewModel.validateCategory(id, new AppCallback() {
            @Override
            public void callback() {
                if(getLifecycle().getCurrentState() == Lifecycle.State.RESUMED) {
                    activityCategoryBinding.lpiCategoryPage.setVisibility(View.GONE);
                    visible = true;
                    invalidateOptionsMenu();
                    //TODO: call for fetching all the items
                }
            }
        }, new AppCallback() {
            @Override
            public void callback() {
                AppAlert.toast(CategoryActivity.this, "Category doesn't exist!");
                if(getLifecycle().getCurrentState() == Lifecycle.State.RESUMED){
                    closeActivity();
                }
            }
        });
    }

    private void setUpActionBar(){
        activityCategoryBinding.tbCategory.setTitle(name);
        setSupportActionBar(activityCategoryBinding.tbCategory);
    }

    private void setUpViews(){
        homeViewModel = new ViewModelProvider(CategoryActivity.this).get(HomeViewModel.class);

        activityCategoryBinding.ivBackCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeActivity();
            }
        });
    }

    private void deleteCategory(){
        ConfirmationDialog confirmationDialog = new ConfirmationDialog(
                CategoryActivity.this,
                AppUtil.firstUpperCase(AppConstant.DELETE),
                AppConstant.CATEGORY_DELETE_CONFIRMATION,
                new AppCallback() {
                    @Override
                    public void callback() {
                        ArrayList<Integer> ids = new ArrayList<>();
                        ids.add(id);
                        homeViewModel.query(RoomDB.DIV.ITEM, RoomDB.QUERY.DELETE_FOR_PARENT, id);
                        homeViewModel.query(RoomDB.DIV.CATEGORY, RoomDB.QUERY.DELETE_SELECTED, ids);
                        closeActivity();
                    }
        });

        confirmationDialog.show(getSupportFragmentManager(), AppConstant.CATEGORY_DIALOG_TAG);
    }

    private void addItem(){
        ItemDialog itemDialog = new ItemDialog(CategoryActivity.this, new ItemDialog.ItemDialogCallback() {
            @Override
            public void callback(Item item) {
                item.setCategoryId(id);
                //TODO: start loading
                homeViewModel.query(RoomDB.DIV.ITEM, RoomDB.QUERY.ADD, item);
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
        for(int i=0;i<menu.size();i++){
            menu.getItem(i).setVisible(visible);
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
}