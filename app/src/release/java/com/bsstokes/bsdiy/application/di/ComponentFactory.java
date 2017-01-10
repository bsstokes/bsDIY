package com.bsstokes.bsdiy.application.di;

import com.bsstokes.bsdiy.BsDiyApplication;

public class ComponentFactory {
    public static AppComponent create(BsDiyApplication application) {
        return DaggerReleaseAppComponent.builder()
                .appModule(new AppModule(application))
                .releaseApiModule(new ReleaseApiModule())
                .build();
    }
}
