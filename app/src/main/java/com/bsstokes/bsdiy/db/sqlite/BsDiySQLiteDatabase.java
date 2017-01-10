package com.bsstokes.bsdiy.db.sqlite;

import android.support.annotation.NonNull;

import com.bsstokes.bsdiy.db.BsDiyDatabase;
import com.squareup.sqlbrite.BriteDatabase;

public class BsDiySQLiteDatabase implements BsDiyDatabase {

    private final BriteDatabase briteDatabase;

    public BsDiySQLiteDatabase(@NonNull BriteDatabase briteDatabase) {
        this.briteDatabase = briteDatabase;
    }
}
