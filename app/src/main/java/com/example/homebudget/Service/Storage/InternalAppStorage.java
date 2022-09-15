package com.example.homebudget.Service.Storage;

import android.content.Context;

import com.example.homebudget.Util.AppAlert;
import com.example.homebudget.Util.AppConstant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class InternalAppStorage {
    private final Context context;

    public InternalAppStorage(Context context){
        this.context = context;
    }

    public File getFile(String fileName){
        boolean status = true;
        File file = new File(context.getFilesDir(), fileName);
        try{
            if(!file.exists()){
                status = file.createNewFile();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return status ? file : null;
    }

    public void writeFile(String fileName, String data){
        try{
            File file = getFile(fileName);
            if(file != null) {
                FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                fileOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
                AppAlert.toast(context, AppConstant.DATA_SAVED);
            }else{
                AppAlert.toast(context, AppConstant.DATA_SAVED_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            AppAlert.toast(context, AppConstant.DATA_SAVED);
        }
    }

    public String readFile(String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        try{
            File file = getFile(fileName);
            if(file != null){
                FileInputStream fileInputStream = context.openFileInput(fileName);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                while(line != null){
                    stringBuilder.append(line).append("\n");
                    line = bufferedReader.readLine();
                }
            }else{
                AppAlert.toast(context, AppConstant.DATA_READ_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            AppAlert.toast(context, AppConstant.DATA_READ_ERROR);
        }
        return stringBuilder.toString().replace("\r", "").replace("\n", "");
    }
}
