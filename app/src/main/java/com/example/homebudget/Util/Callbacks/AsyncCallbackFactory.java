package com.example.homebudget.Util.Callbacks;

public class AsyncCallbackFactory implements Runnable {

    private final AsyncCallback asyncCallback;

    AsyncCallbackFactory(AsyncCallback asyncCallback){
        this.asyncCallback = asyncCallback;
    }

    public interface AsyncCallback{
        public void run();
    }

    @Override
    public void run() {
        asyncCallback.run();
    }
}
