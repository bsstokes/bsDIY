package com.bsstokes.bsdiy.db.sqlite.migrations;

import android.database.sqlite.SQLiteDatabase;

public class Migration001_CreateSkills implements Migration {

    @Override
    public void onUpgrade(SQLiteDatabase database) {
        final String CREATE_SKILLS = ""
                + "CREATE TABLE `skills` ("
                + "`_id` BIGINT PRIMARY KEY"
                + ");";
        database.execSQL(CREATE_SKILLS);
    }
}
