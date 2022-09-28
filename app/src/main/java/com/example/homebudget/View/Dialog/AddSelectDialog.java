package com.example.homebudget.View.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.homebudget.Model.Selection;
import com.example.homebudget.Util.AppConstant;


/**
 *     private Selection selection;
 *     private String[] arr;
 *     private String title, positiveText, negativeText;
 *     private boolean cancelable;
 *     private int selected;
 *     private SelectedResultCallback selectedResultCallback;
 */
public class AddSelectDialog extends DialogFragment {
    private Context context;
    private Selection selection;
    private String[] arr;
    private String title, positiveText, negativeText;
    private boolean cancelable;
    private int selectedIndex;
    private SelectedResultCallback selectedResultCallback;

    public interface SelectedResultCallback{
        public void selected(Selection.Selected selected, int selectedIndex);
    }

    public AddSelectDialog(Context context, boolean cancelable, int selectedIndex, SelectedResultCallback selectedResultCallback){
        this.context = context;

        this.cancelable = cancelable;
        this.selectedIndex = selectedIndex;
        this.selectedResultCallback = selectedResultCallback;

        this.selection = new Selection();
        this.arr = new String[]{AppConstant.DASHBOARD, AppConstant.PLAN};
        this.title = AppConstant.CHOOSE;
        this.positiveText = AppConstant.OK;
        this.negativeText = AppConstant.CANCEL;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);

        builder.setSingleChoiceItems(arr, selectedIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selectedIndex = i;
            }
        });

        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(arr[selectedIndex].equals(AppConstant.DASHBOARD)){
                    selection.setSelected(Selection.Selected.CATEGORY);
                }else if(arr[selectedIndex].equals(AppConstant.PLAN)){
                    selection.setSelected(Selection.Selected.PLAN);
                }
                selectedResultCallback.selected(selection.getSelected(), selectedIndex);
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

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }
}