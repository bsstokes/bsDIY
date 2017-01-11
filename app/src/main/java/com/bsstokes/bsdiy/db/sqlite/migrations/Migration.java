package com.bsstokes.bsdiy.db.sqlite.migrations;

import android.database.sqlite.SQLiteDatabase;

public interface Migration {
    void onUpgrade(SQLiteDatabase database);
}
