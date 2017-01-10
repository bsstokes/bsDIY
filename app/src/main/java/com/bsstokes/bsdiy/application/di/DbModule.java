package com.bsstokes.bsdiy.application.di;

import com.bsstokes.bsdiy.db.BsDiyDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class DbModule {

    @Provides
    @Singleton
    BsDiyDatabase provideDatabase() {
        return new BsDiyDatabase();
    }
}
