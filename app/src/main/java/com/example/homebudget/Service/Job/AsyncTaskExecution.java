package com.example.homebudget.Service.Job;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AsyncTaskExecution {
    Runnable onPreExecute, doInBackground, onSuccess, onError;

    public AsyncTaskExecution(Runnable onPreExecute, @NonNull Runnable doInBackground, Runnable onSuccess, Runnable onError) {
        this.onPreExecute = onPreExecute;
        this.doInBackground = doInBackground;
        this.onSuccess = onSuccess;
        this.onError = onError;
    }

    public void execute(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(prepareRunnable());
    }

    public void execute(Long timeInMilliSeconds){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(prepareRunnable(), timeInMilliSeconds, TimeUnit.MILLISECONDS);
    }

    private Runnable prepareRunnable(){
        Handler handler = new Handler(Looper.getMainLooper());
        return new Runnable() {
            @Override
            public void run() {
                if(onPreExecute != null) handler.post(onPreExecute);

                try{
                    if(doInBackground != null) doInBackground.run();
                    if(onSuccess != null) handler.post(onSuccess);
                }catch(Exception e){
                    e.printStackTrace();
                    if(onError != null) handler.post(onError);
                }
            }
        };
    }
}
