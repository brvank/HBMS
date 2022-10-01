package com.example.homebudget.View.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.homebudget.Util.AppConstant;
import com.example.homebudget.Util.Callbacks.AppCallback;

public class ConfirmationDialog extends DialogFragment {
    private final Context context;
    private final String title, message;
    private final AppCallback appCallback;

    public ConfirmationDialog(Context context, String title, String message, AppCallback appCallback){
        this.context = context;
        this.title = title;
        this.message = message;
        this.appCallback = appCallback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton(AppConstant.DELETE, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                appCallback.callback();
            }
        });

        builder.setNegativeButton(AppConstant.CANCEL, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }
}
