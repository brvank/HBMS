package com.example.homebudget.View.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.homebudget.Util.AppConstant;

public class MessageDialog extends AppDialog {
    private final Context context;
    private final String title;
    private final String message;
    private final String positiveText;

    public MessageDialog(Context context,String title, String message){
        this.context = context;
        this.title = title;
        this.message = message;
        this.positiveText = AppConstant.OK;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        builder.setCancelable(true);

        return builder.create();
    }
}
