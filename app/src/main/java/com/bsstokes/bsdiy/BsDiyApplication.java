package com.bsstokes.bsdiy;

import android.app.Application;

import com.bsstokes.bsdiy.application.initializers.StethoInitializer;

public class BsDiyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        StethoInitializer.initialize(this);
    }
}
