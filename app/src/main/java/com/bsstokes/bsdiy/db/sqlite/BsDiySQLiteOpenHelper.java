package com.bsstokes.bsdiy.db.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.bsstokes.bsdiy.db.sqlite.migrations.Migration;
import com.bsstokes.bsdiy.db.sqlite.migrations.Migration001_CreateSkills;
import com.bsstokes.bsdiy.db.sqlite.migrations.Migration002_CreateChallenges;
import com.bsstokes.bsdiy.db.sqlite.migrations.Migration003_InsertDummyData;
import com.bsstokes.bsdiy.db.sqlite.migrations.Migrator;

public class BsDiySQLiteOpenHelper extends SQLiteOpenHelper {

    public static BsDiySQLiteOpenHelper create(@NonNull Context context) {
        return new BsDiySQLiteOpenHelper(context, DATABASE_NAME);
    }

    public static BsDiySQLiteOpenHelper createInMemory(@NonNull Context context) {
        return new BsDiySQLiteOpenHelper(context, IN_MEMORY_DATABASE_NAME);
    }

    private static final String DATABASE_NAME = "bsdiy.db";
    private static final String IN_MEMORY_DATABASE_NAME = null;
    private static final SQLiteDatabase.CursorFactory NULL_CURSOR_FACTORY = null;

    private static final Migrator MIGRATOR = new Migrator(new Migration[]{
            new Migration001_CreateSkills(),
            new Migration002_CreateChallenges(),
            new Migration003_InsertDummyData()
    });

    private BsDiySQLiteOpenHelper(@NonNull Context context, String databaseName) {
        super(context, databaseName, NULL_CURSOR_FACTORY, MIGRATOR.getVersion());
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
