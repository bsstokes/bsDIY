package com.bsstokes.bsdiy.db.sqlite.migrations;

import android.database.sqlite.SQLiteDatabase;

public class Migrator {

    private final Migration[] migrations;

    public Migrator(Migration[] migrations) {
        this.migrations = migrations;
    }

    public void migrate(SQLiteDatabase database, int oldVersion, int newVersion) {
        for (int version = oldVersion; version < newVersion; ++version) {
            migrations[version].onUpgrade(database);
        }
    }

    public int getVersion() {
        return migrations.length;
    }
}
