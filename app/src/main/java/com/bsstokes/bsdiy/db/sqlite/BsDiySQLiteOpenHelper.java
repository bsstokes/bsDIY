package com.bsstokes.bsdiy.db.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.bsstokes.bsdiy.db.sqlite.migrations.Migration;
import com.bsstokes.bsdiy.db.sqlite.migrations.Migration001_CreateSkills;
import com.bsstokes.bsdiy.db.sqlite.migrations.Migration002_CreateChallenges;
import com.bsstokes.bsdiy.db.sqlite.migrations.Migrator;

public class BsDiySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bsdiy.db";
    private static final SQLiteDatabase.CursorFactory NULL_CURSOR_FACTORY = null;

    private static final Migrator MIGRATOR = new Migrator(new Migration[]{
            new Migration001_CreateSkills(),
            new Migration002_CreateChallenges()
    });

    public BsDiySQLiteOpenHelper(@NonNull Context context) {
        super(context, DATABASE_NAME, NULL_CURSOR_FACTORY, MIGRATOR.getVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        MIGRATOR.migrate(db, 0, MIGRATOR.getVersion());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MIGRATOR.migrate(db, oldVersion, newVersion);
    }
}
