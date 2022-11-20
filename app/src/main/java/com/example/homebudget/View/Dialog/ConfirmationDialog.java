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

public class ConfirmationDialog extends AppDialog {
    private final Context context;
    private final String title, message;
    private final Runnable runnable;

    public ConfirmationDialog(Context context, String title, String message, Runnable runnable){
        this.context = context;
        this.title = title;
        this.message = message;
        this.runnable = runnable;
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
                runnable.run();
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
}
