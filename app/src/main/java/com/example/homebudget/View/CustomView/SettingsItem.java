package com.example.homebudget.View.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.TextViewCompat;

import com.example.homebudget.R;
import com.example.homebudget.Util.Callbacks.UpdateCallback;

public class SettingsItem extends LinearLayout {
    private final TypedArray attributes;
    private final TextView tvTitle;
    private final CheckBox cbState;
    private UpdateCallback callback;

    public SettingsItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.settings_item, this);
        attributes = context.obtainStyledAttributes(attrs, R.styleable.SettingsItem);

        tvTitle = findViewById(R.id.tvTitle);
        cbState = findViewById(R.id.cbState);

        tvTitle.setText(attributes.getString(R.styleable.SettingsItem_title));
        cbState.setChecked(attributes.getBoolean(R.styleable.SettingsItem_state, false));

        cbState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkBoxCheckedListener(b);
            }
        });
    }

    private void checkBoxCheckedListener(boolean b){
        if(callback != null){
            callback.update(b);
        }
        switchTitleVisibility(b);
    }

    public void setCallback(UpdateCallback cb){
        this.callback = cb;
    }

    public void setToggleState(boolean state){
        cbState.setChecked(state);
        switchTitleVisibility(state);
    }

    public boolean getToggleState(){
        return cbState.isChecked();
    }

    public void setTitleText(String title){
        tvTitle.setText(title);
    }

    private void switchTitleVisibility(boolean state){
        if(state){
            TextViewCompat.setTextAppearance(tvTitle, R.style.SettingsItemTitleBold);
        }else{
            TextViewCompat.setTextAppearance(tvTitle, R.style.SettingsItemTitleNormal);
        }
    }
}
