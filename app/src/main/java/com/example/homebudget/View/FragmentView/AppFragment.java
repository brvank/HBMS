package com.example.homebudget.View.FragmentView;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import com.example.homebudget.Util.AppConstant;
import com.example.homebudget.View.Dialog.MessageDialog;

public class AppFragment extends Fragment {
    public boolean mounted(){
        return getLifecycle().getCurrentState() == Lifecycle.State.RESUMED;
    }

    public void showMessage(Context context, String title, String message){
        MessageDialog messageDialog = new MessageDialog(context, title, message);
        messageDialog.show(getParentFragmentManager(), AppConstant.MESSAGE_DIALOG_TAG);
    }
}
