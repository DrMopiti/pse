package com.example.user.schachapp;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

public class ThreadHandler {

    private final HandlerThread backgroundHandlerThread;
    private final Handler backgroundHandler;
    private final Handler foregroundHandler = new Handler(Looper.getMainLooper());

    public ThreadHandler() {
        backgroundHandlerThread = new HandlerThread("background");
        backgroundHandlerThread.start();
        backgroundHandler = new Handler(backgroundHandlerThread.getLooper());
    }

    public void runInBackground(Runnable action) {
        backgroundHandler.post(action);
    }

    public void runInForeground(Runnable action) {
        foregroundHandler.post(action);
    }
}
