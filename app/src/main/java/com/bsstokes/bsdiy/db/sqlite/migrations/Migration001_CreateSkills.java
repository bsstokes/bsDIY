package com.bsstokes.bsdiy.db.sqlite.migrations;

import android.database.sqlite.SQLiteDatabase;

public class Migration001_CreateSkills implements Migration {

    @Override
    public void onUpgrade(SQLiteDatabase database) {
        final String CREATE_SKILLS = ""
                + "CREATE TABLE `skills` ("
                + "`_id` BIGINT PRIMARY KEY, "
                + "`active` SMALLINT DEFAULT 1, "
                + "`url` VARCHAR, "
                + "`title` VARCHAR, "
                + "`description` VARCHAR, "
                + "`image_small` VARCHAR, "
                + "`image_medium` VARCHAR, "
                + "`image_large` VARCHAR, "
                + "`color` VARCHAR, "
                + "`priority` INTEGER"
                + ");";
        database.execSQL(CREATE_SKILLS);
    }
}
