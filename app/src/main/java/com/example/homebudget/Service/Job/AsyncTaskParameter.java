package com.example.homebudget.Service.Job;

public class AsyncTaskParameter {
    private Runnable onPreExecute, onSuccess, onError, doInBackground;

    //all setters
    public void setOnPreExecute(Runnable onPreExecute) {
        this.onPreExecute = onPreExecute;
    }

    public void setOnSuccess(Runnable onSuccess) {
        this.onSuccess = onSuccess;
    }

    public void setOnError(Runnable onError) {
        this.onError = onError;
    }

    public void setDoInBackground(Runnable doInBackground) {
        this.doInBackground = doInBackground;
    }

    //all getters
    public Runnable getOnPreExecute() {
        return onPreExecute;
    }

    public Runnable getOnSuccess() {
        return onSuccess;
    }

    public Runnable getOnError() {
        return onError;
    }

    public Runnable getDoInBackground() {
        return doInBackground;
    }

    //builder class
    public static class Builder{
        private final AsyncTaskParameter asyncTaskParameter;

        public Builder(){
            asyncTaskParameter = new AsyncTaskParameter();
        }

        public AsyncTaskParameter.Builder setOnPreExecute(Runnable runnable){
            asyncTaskParameter.setOnPreExecute(runnable);
            return this;
        }

        public AsyncTaskParameter.Builder setDoInBackground(Runnable runnable){
            asyncTaskParameter.setDoInBackground(runnable);
            return this;
        }

        public AsyncTaskParameter.Builder setOnSuccess(Runnable runnable){
            asyncTaskParameter.setOnSuccess(runnable);
            return this;
        }

        public AsyncTaskParameter.Builder setOnError(Runnable runnable){
            asyncTaskParameter.setOnError(runnable);
            return this;
        }

        public AsyncTaskParameter build(){
            return asyncTaskParameter;
        }
    }
}
