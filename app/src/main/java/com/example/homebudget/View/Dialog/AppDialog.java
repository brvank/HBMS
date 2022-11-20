package com.example.homebudget.View.Dialog;

import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public abstract class AppDialog extends DialogFragment {
    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }
}
