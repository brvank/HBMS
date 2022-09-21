package com.example.homebudget.View.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.homebudget.Model.Selection;
import com.example.homebudget.Util.AppConstant;

public class AddDialogFragment extends DialogFragment {
    private final Selection selection;
    private final String[] arr;
    private final String title;
    private final String positiveText;
    private final String negativeText;
    private final boolean cancelable;
    private int selected;
    private final SelectedResultCallback selectedResultCallback;

    public static interface SelectedResultCallback{
        public void selected(Selection.Selected selected, int selectedIndex);
    }

    public AddDialogFragment(SelectedResultCallback selectedResultCallback){
        this.selection = new Selection();
        this.arr = new String[]{AppConstant.DASHBOARD, AppConstant.PLAN};
        this.title = "Choose";
        this.positiveText = "ADD";
        this.negativeText = "CANCEL";
        this.cancelable = true;
        this.selected = 0;
        this.selectedResultCallback = selectedResultCallback;
    }

    public AddDialogFragment(SelectedResultCallback selectedResultCallback, int selected){
        this.selection = new Selection();
        this.arr = new String[]{AppConstant.DASHBOARD, AppConstant.PLAN};
        this.title = "Choose";
        this.positiveText = "ADD";
        this.negativeText = "CANCEL";
        this.cancelable = true;
        this.selectedResultCallback = selectedResultCallback;
        if(selected >= arr.length){
            this.selected = 0;
        }else{
            this.selected = selected;
        }
    }

    public AddDialogFragment(SelectedResultCallback selectedResultCallback, boolean cancelable){
        this.selection = new Selection();
        this.arr = new String[]{AppConstant.DASHBOARD, AppConstant.PLAN};
        this.title = "Choose";
        this.positiveText = "ADD";
        this.negativeText = "CANCEL";
        this.cancelable = cancelable;
        this.selected = 0;
        this.selectedResultCallback = selectedResultCallback;
    }

    public AddDialogFragment(SelectedResultCallback selectedResultCallback, int selected, boolean cancelable){
        this.selection = new Selection();
        this.arr = new String[]{AppConstant.DASHBOARD, AppConstant.PLAN};
        this.title = "Choose";
        this.positiveText = "ADD";
        this.negativeText = "CANCEL";
        this.cancelable = cancelable;
        this.selectedResultCallback = selectedResultCallback;
        if(selected >= arr.length){
            this.selected = 0;
        }else{
            this.selected = selected;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(title);

        builder.setSingleChoiceItems(arr, selected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selected = i;
            }
        });

        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(arr[selected].equals(AppConstant.DASHBOARD)){
                    selection.setSelected(Selection.Selected.CATEGORY);
                }else if(arr[selected].equals(AppConstant.PLAN)){
                    selection.setSelected(Selection.Selected.PLAN);
                }
                selectedResultCallback.selected(selection.getSelected(), selected);
            }
        });

        builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setCancelable(cancelable);

        return builder.create();
    }
}