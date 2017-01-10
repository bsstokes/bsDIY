package com.bsstokes.bsdiy;

import android.app.Application;
import android.util.Log;

public class BsDiyApplication extends Application {
    private static final String TAG = "BsDiyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "I'm a custom Application.");
    }
}
