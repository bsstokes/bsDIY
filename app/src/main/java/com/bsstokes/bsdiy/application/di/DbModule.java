package com.bsstokes.bsdiy.application.di;

import com.bsstokes.bsdiy.application.BsDiyApplication;
import com.bsstokes.bsdiy.db.BsDiyDatabase;
import com.bsstokes.bsdiy.db.sqlite.BsDiySQLiteDatabase;
import com.bsstokes.bsdiy.db.sqlite.BsDiySQLiteOpenHelper;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

@Module
class DbModule {

    @Provides
    @Singleton
    BsDiyDatabase provideDatabase(BriteDatabase briteDatabase) {
        return new BsDiySQLiteDatabase(briteDatabase);
    }

    @Provides
    @Singleton
    BriteDatabase provideBriteDatabase(BsDiySQLiteOpenHelper openHelper) {
        final SqlBrite sqlBrite = new SqlBrite.Builder().build();
        return sqlBrite.wrapDatabaseHelper(openHelper, Schedulers.io());
    }

    @Provides
    @Singleton
    BsDiySQLiteOpenHelper provideBsDiySQLiteOpenHelper(BsDiyApplication application) {
        return new BsDiySQLiteOpenHelper(application.getApplicationContext());
    }
}
