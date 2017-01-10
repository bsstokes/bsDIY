package com.bsstokes.bsdiy.db.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

public class BsDiySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bsdiy.db";
    private static final int CURRENT_VERSION = 1;
    private static final SQLiteDatabase.CursorFactory NULL_CURSOR_FACTORY = null;

    public BsDiySQLiteOpenHelper(@NonNull Context context) {
        super(context, DATABASE_NAME, NULL_CURSOR_FACTORY, CURRENT_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
