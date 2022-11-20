package com.example.homebudget.View.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.homebudget.Model.Category;
import com.example.homebudget.Util.AppConstant;
import com.example.homebudget.Util.AppUtil;
import com.example.homebudget.databinding.LayoutCategoryAddDialogBinding;

import java.util.Objects;

public class CategoryDialog extends AppDialog {
    private final int nameLimit, infoLimit;
    private final Context context;
    private Category category;
    private final CategoryDialogCallback categoryDialogCallback;
    private LayoutCategoryAddDialogBinding layoutCategoryAddDialogBinding;

    public CategoryDialog(Context context, CategoryDialogCallback categoryDialogCallback){
        this.context = context;
        this.categoryDialogCallback = categoryDialogCallback;
        this.category = null;
        this.nameLimit = AppConstant.NAME_MAX_LENGTH;
        this.infoLimit = AppConstant.INFO_MAX_LENGTH;
    }

    public CategoryDialog(Context context, CategoryDialogCallback categoryDialogCallback, Category category){
        this.context = context;
        this.categoryDialogCallback = categoryDialogCallback;
        this.category = category;
        this.nameLimit = AppConstant.NAME_MAX_LENGTH;
        this.infoLimit = AppConstant.INFO_MAX_LENGTH;
    }

    public interface CategoryDialogCallback {
        public void callback(Category category);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutCategoryAddDialogBinding = LayoutCategoryAddDialogBinding.inflate(inflater, container, false);
        Objects.requireNonNull(getDialog()).setCancelable(false);
        return layoutCategoryAddDialogBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(category != null){
            layoutCategoryAddDialogBinding.etCategoryName.setText(category.getName());
            layoutCategoryAddDialogBinding.etCategoryInfo.setText(category.getInfo());
            layoutCategoryAddDialogBinding.tvAddButton.setText(AppConstant.UPDATE);
        }

        layoutCategoryAddDialogBinding.etCategoryName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String name = editable.toString();
                if(name.isEmpty()){
                    layoutCategoryAddDialogBinding.tilCategoryName.setError(AppConstant.CATEGORY_NAME_REQUIRED);
                }else if(name.length() > nameLimit){
                    layoutCategoryAddDialogBinding.tilCategoryName.setError(AppConstant.CATEGORY_NAME_LENGTH_CROSSED);
                }else{
                    layoutCategoryAddDialogBinding.tilCategoryName.setError(AppConstant.ZERO_LENGTH_STRING);
                }
            }
        });

        layoutCategoryAddDialogBinding.etCategoryInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String info = editable.toString();
                if(info.isEmpty()){
                    layoutCategoryAddDialogBinding.tilCategoryInfo.setError(AppConstant.CATEGORY_INFO_REQUIRED);
                }else if(info.length() > infoLimit){
                    layoutCategoryAddDialogBinding.tilCategoryInfo.setError(AppConstant.CATEGORY_INFO_LENGTH_CROSSED);
                }else{
                    layoutCategoryAddDialogBinding.tilCategoryInfo.setError(AppConstant.ZERO_LENGTH_STRING);
                }
            }
        });

        layoutCategoryAddDialogBinding.tvCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        layoutCategoryAddDialogBinding.tvAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnCategoryOnValidate();
            }
        });

        layoutCategoryAddDialogBinding.etCategoryName.requestFocus();
    }

    private void returnCategoryOnValidate(){
        if(categoryFieldsValidate()){
            String name = Objects.requireNonNull(layoutCategoryAddDialogBinding.etCategoryName.getText()).toString();
            String info = Objects.requireNonNull(layoutCategoryAddDialogBinding.etCategoryInfo.getText()).toString();
            if(category == null){
                category = new Category(name, info, AppUtil.todayDate());
            }else{
                category.setName(name);
                category.setInfo(info);
            }

            dismiss();
            categoryDialogCallback.callback(category);
        }
    }

    private boolean categoryFieldsValidate(){
        String name = Objects.requireNonNull(layoutCategoryAddDialogBinding.etCategoryName.getText()).toString();
        String info = Objects.requireNonNull(layoutCategoryAddDialogBinding.etCategoryInfo.getText()).toString();
        boolean nameFocus = false, infoFocus = false;
        if((!name.isEmpty() && name.length() <= nameLimit) && (!info.isEmpty() && info.length() <= infoLimit)){
            return true;
        }else{
            if(name.isEmpty()){
                layoutCategoryAddDialogBinding.tilCategoryName.setError(AppConstant.CATEGORY_NAME_REQUIRED);
                nameFocus = true;
            }else if(name.length() > nameLimit){
                layoutCategoryAddDialogBinding.tilCategoryName.setError(AppConstant.CATEGORY_NAME_LENGTH_CROSSED);
                nameFocus = true;
            }

            if(info.isEmpty()){
                layoutCategoryAddDialogBinding.tilCategoryInfo.setError(AppConstant.CATEGORY_INFO_REQUIRED);
                infoFocus = true;
            }else if(info.length() > infoLimit){
                layoutCategoryAddDialogBinding.tilCategoryInfo.setError(AppConstant.CATEGORY_INFO_LENGTH_CROSSED);
                infoFocus = true;
            }

            if(nameFocus){
                layoutCategoryAddDialogBinding.etCategoryName.requestFocus();
            }else if(infoFocus){
                layoutCategoryAddDialogBinding.etCategoryInfo.requestFocus();
            }
            return false;
        }
    }
}
