package com.example.homebudget.View.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.homebudget.Model.Item;
import com.example.homebudget.Util.AppConstant;
import com.example.homebudget.Util.AppUtil;
import com.example.homebudget.databinding.LayoutItemAddDialogBinding;

import java.util.Objects;

public class ItemDialog extends DialogFragment {
    private final int nameLimit, infoLimit;
    private Context context;
    private Item item;
    private ItemDialogCallback itemDialogCallback;
    private LayoutItemAddDialogBinding layoutItemAddDialogBinding;

    public ItemDialog(Context context, ItemDialogCallback itemDialogCallback){
        this.context = context;
        this.itemDialogCallback = itemDialogCallback;
        this.item = null;
        this.nameLimit = AppConstant.NAME_MAX_LENGTH;
        this.infoLimit = AppConstant.INFO_MAX_LENGTH;
    }
    
    public ItemDialog(Context context, ItemDialogCallback itemDialogCallback, Item item){
        this.context = context;
        this.itemDialogCallback = itemDialogCallback;
        this.item = item;
        this.nameLimit = AppConstant.NAME_MAX_LENGTH;
        this.infoLimit = AppConstant.INFO_MAX_LENGTH;
    }
    
    public interface ItemDialogCallback{
        public void callback(Item item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutItemAddDialogBinding = LayoutItemAddDialogBinding.inflate(inflater, container, false);
        Objects.requireNonNull(getDialog()).setCancelable(false);
        return layoutItemAddDialogBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(item != null){
            layoutItemAddDialogBinding.etItemName.setText(item.getName());
            layoutItemAddDialogBinding.etItemInfo.setText(item.getId());
            layoutItemAddDialogBinding.etItemCurrentValue.setText(item.getCurrent());
            layoutItemAddDialogBinding.cbCreateNewItem.setVisibility(View.GONE);
            layoutItemAddDialogBinding.spnrItemsName.setVisibility(View.GONE);
            layoutItemAddDialogBinding.llPreviousItem.setVisibility(View.VISIBLE);
            layoutItemAddDialogBinding.tvAddButton.setText(AppConstant.UDPATE);
        }else{
            udpateCreateOptionsView(true);
            layoutItemAddDialogBinding.cbCreateNewItem.setChecked(true);
            layoutItemAddDialogBinding.cbCreateNewItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    udpateCreateOptionsView(b);
                }
            });
        }

        layoutItemAddDialogBinding.etItemName.addTextChangedListener(new TextWatcher() {
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
                    layoutItemAddDialogBinding.tilItemName.setError(AppConstant.ITEM_NAME_REQUIRED);
                }else if(name.length() > nameLimit){
                    layoutItemAddDialogBinding.tilItemName.setError(AppConstant.ITEM_NAME_LENGTH_CROSSED);
                }else{
                    layoutItemAddDialogBinding.tilItemName.setError(AppConstant.ZERO_LENGTH_STRING);
                }
            }
        });

        layoutItemAddDialogBinding.etItemInfo.addTextChangedListener(new TextWatcher() {
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
                    layoutItemAddDialogBinding.tilItemInfo.setError(AppConstant.ITEM_INFO_REQUIRED);
                }else if(info.length() > infoLimit){
                    layoutItemAddDialogBinding.tilItemInfo.setError(AppConstant.ITEM_INFO_LENGTH_CROSSED);
                }else{
                    layoutItemAddDialogBinding.tilItemInfo.setError(AppConstant.ZERO_LENGTH_STRING);
                }
            }
        });

        layoutItemAddDialogBinding.tvCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        layoutItemAddDialogBinding.tvAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnItemOnValidate();
            }
        });
        
    }
    
    private void udpateCreateOptionsView(boolean createNew){
        int visibility = AppUtil.visibility(createNew);
        int reverseVisibility = AppUtil.visibility(!createNew);
        layoutItemAddDialogBinding.spnrItemsName.setVisibility(reverseVisibility);
        layoutItemAddDialogBinding.tilItemName.setVisibility(visibility);
        layoutItemAddDialogBinding.tilItemInfo.setVisibility(visibility);
        layoutItemAddDialogBinding.llPreviousItem.setVisibility(reverseVisibility);
    }

    private void returnItemOnValidate(){
        if(itemFieldsValidate()){
            String name = Objects.requireNonNull(layoutItemAddDialogBinding.etItemName.getText()).toString();
            String info = Objects.requireNonNull(layoutItemAddDialogBinding.etItemInfo.getText()).toString();
            String current = Objects.requireNonNull(layoutItemAddDialogBinding.etItemCurrentValue.getText()).toString();
            int currentValue = Integer.parseUnsignedInt(current);
            if(item == null){
                item = new Item(name, info, currentValue, 0, 0);
            }else{
                item.setName(name);
                item.setInfo(info);
                item.setCurrent(currentValue);
            }

            dismiss();
            itemDialogCallback.callback(item);
        }
    }
    
    private boolean itemFieldsValidate(){
        String name = Objects.requireNonNull(layoutItemAddDialogBinding.etItemName.getText()).toString();
        String info = Objects.requireNonNull(layoutItemAddDialogBinding.etItemInfo.getText()).toString();
        String current = Objects.requireNonNull(layoutItemAddDialogBinding.etItemCurrentValue.getText()).toString();
        int currentValue = Integer.parseUnsignedInt(current);
        boolean nameFocus = false, infoFocus = false, currentFocus = false;
        if((!name.isEmpty() && name.length() <= nameLimit) && (!info.isEmpty() && info.length() <= nameLimit) && (currentValue > 0)){
            return true;
        }else{
            if(name.isEmpty()){
                layoutItemAddDialogBinding.tilItemName.setError(AppConstant.ITEM_NAME_REQUIRED);
                nameFocus = true;
            }else if(name.length() > nameLimit){
                layoutItemAddDialogBinding.tilItemName.setError(AppConstant.ITEM_NAME_LENGTH_CROSSED);
                nameFocus = true;
            }

            if(info.isEmpty()){
                layoutItemAddDialogBinding.tilItemInfo.setError(AppConstant.ITEM_INFO_REQUIRED);
                infoFocus = true;
            }else if(info.length() > infoLimit){
                layoutItemAddDialogBinding.tilItemInfo.setError(AppConstant.ITEM_INFO_LENGTH_CROSSED);
                infoFocus = true;
            }
            
            if(currentValue < 0){
                layoutItemAddDialogBinding.etItemCurrentValue.setError(AppConstant.ENTER_NON_ZERO_POSITIVE);
            }
            
            if(nameFocus){
                layoutItemAddDialogBinding.etItemName.requestFocus();
            }else if(infoFocus){
                layoutItemAddDialogBinding.etItemInfo.requestFocus();
            }
            
            return false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }
}
