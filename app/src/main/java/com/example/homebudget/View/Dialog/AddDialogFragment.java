package com.example.homebudget.View.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.homebudget.Model.Selection;
import com.example.homebudget.R;
import com.example.homebudget.Util.AppAlert;
import com.example.homebudget.Util.AppConstant;
import com.example.homebudget.databinding.ActivityGetInBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AddDialogFragment extends DialogFragment {
    final Selection selection;
    String[] arr;
    String title;
    String positiveText;
    String negativeText;
    SelectedResultCallback selectedResultCallback;

    public static interface SelectedResultCallback{
        public void selected(Selection.Selected selected);
    }

    public AddDialogFragment(SelectedResultCallback selectedResultCallback){
        selection = new Selection();
        arr = new String[]{AppConstant.DASHBOARD, AppConstant.PLAN};
        title = "Choose";
        positiveText = "ADD";
        negativeText = "CANCEL";

        this.selectedResultCallback = selectedResultCallback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(title);

        builder.setSingleChoiceItems(arr, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(arr[i].equals(AppConstant.DASHBOARD)){
                    selection.setSelected(Selection.Selected.CATEGORY);
                }else if(arr[i].equals(AppConstant.PLAN)){
                    selection.setSelected(Selection.Selected.ITEM);
                }
            }
        });

        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AppAlert.toastLong(getContext(), String.valueOf(i));
            }
        });

        builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AppAlert.toastLong(getContext(), String.valueOf(i));
            }
        });

        return builder.create();
    }
}