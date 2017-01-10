package com.bsstokes.bsdiy;

import android.app.Application;

import com.bsstokes.bsdiy.application.di.AppComponent;
import com.bsstokes.bsdiy.application.di.ComponentFactory;
import com.bsstokes.bsdiy.application.initializers.StethoInitializer;

public class BsDiyApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = ComponentFactory.create(this);
        StethoInitializer.initialize(this);
    }

    public AppComponent appComponent() {
        return appComponent;
    }
}
