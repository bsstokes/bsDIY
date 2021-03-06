package com.bsstokes.bsdiy.application.di;

import com.bsstokes.bsdiy.application.BsDiyApplication;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class AppModule {

    private final BsDiyApplication application;

    AppModule(BsDiyApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    BsDiyApplication provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    Picasso providePicasso() {
        return Picasso.with(application);
    }
}
