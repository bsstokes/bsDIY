package com.bsstokes.bsdiy.application;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.support.annotation.NonNull;

import com.bsstokes.bsdiy.application.di.AppComponent;
import com.bsstokes.bsdiy.application.di.ComponentFactory;
import com.bsstokes.bsdiy.application.initializers.StethoInitializer;
import com.crashlytics.android.Crashlytics;
import com.squareup.leakcanary.LeakCanary;

import io.fabric.sdk.android.Fabric;

public class BsDiyApplication extends Application {

    @NonNull
    public static BsDiyApplication getApplication(@NonNull Activity activity) {
        return (BsDiyApplication) activity.getApplication();
    }

    @NonNull
    public static BsDiyApplication getApplication(@NonNull Service service) {
        return (BsDiyApplication) service.getApplication();
    }

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        Fabric.with(this, new Crashlytics());

        appComponent = ComponentFactory.create(this);
        appComponent.inject(this);

        StethoInitializer.initialize(this);
    }

    public AppComponent appComponent() {
        return appComponent;
    }
}
