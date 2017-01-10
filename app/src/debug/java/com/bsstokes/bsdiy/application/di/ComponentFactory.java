package com.bsstokes.bsdiy.application.di;

import android.support.annotation.NonNull;

import com.bsstokes.bsdiy.application.BsDiyApplication;

public class ComponentFactory {
    public static AppComponent create(@NonNull BsDiyApplication application) {
        return DaggerDebugAppComponent.builder()
                .appModule(new AppModule(application))
                .debugApiModule(new DebugApiModule())
                .build();
    }
}
