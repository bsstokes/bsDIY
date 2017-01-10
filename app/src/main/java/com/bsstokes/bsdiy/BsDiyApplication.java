package com.bsstokes.bsdiy;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bsstokes.bsdiy.application.di.AppComponent;
import com.bsstokes.bsdiy.application.di.ComponentFactory;
import com.bsstokes.bsdiy.application.initializers.StethoInitializer;

import javax.inject.Inject;
import javax.inject.Named;

import static com.bsstokes.bsdiy.application.di.ApiModule.USER_AGENT;

public class BsDiyApplication extends Application {

    @NonNull
    public static BsDiyApplication getApplication(@NonNull Activity activity) {
        return (BsDiyApplication) activity.getApplication();
    }

    @Inject @Named(USER_AGENT) String userAgent;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = ComponentFactory.create(this);
        appComponent.inject(this);

        StethoInitializer.initialize(this);

        Log.d("BsDiyApplication", "userAgent=" + userAgent);
    }

    public AppComponent appComponent() {
        return appComponent;
    }
}
