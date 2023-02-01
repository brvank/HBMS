package com.example.homebudget.View;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.example.homebudget.Util.AppConstant;
import com.example.homebudget.View.Dialog.MessageDialog;

public abstract class AppActivity extends AppCompatActivity {

    public boolean mounted(){
        return getLifecycle().getCurrentState() == Lifecycle.State.RESUMED;
    }

    public void showMessage(Context context, String title, String message){
        MessageDialog messageDialog = new MessageDialog(context, title, message);
        messageDialog.show(getSupportFragmentManager(), AppConstant.MESSAGE_DIALOG_TAG);
    }

    public abstract void setLoading(boolean status);
}
