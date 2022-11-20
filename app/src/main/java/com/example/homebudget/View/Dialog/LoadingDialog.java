package com.example.homebudget.View.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.homebudget.databinding.LayoutLoadingBinding;

public class LoadingDialog extends AppDialog {

    private final String message;
    private final Context context;

    public LoadingDialog(Context context, String message){
        this.message = message;
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutLoadingBinding layoutLoadingBinding = LayoutLoadingBinding.inflate(getLayoutInflater());
        layoutLoadingBinding.tvLoadingMessage.setText(message);

        builder.setView(layoutLoadingBinding.getRoot());

        return builder.create();
    }
}
