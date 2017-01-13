package com.bsstokes.bsdiy.db.sqlite.migrations;

import android.database.sqlite.SQLiteDatabase;

public class Migration002_CreateChallenges implements Migration {

    @Override
    public void onUpgrade(SQLiteDatabase database) {
        final String CREATE_CHALLENGES = ""
                + "CREATE TABLE `challenges` ("
                + "`_id` BIGINT PRIMARY KEY, "
                + "`skill_id` BIGINT, "
                + "`active` SMALLINT DEFAULT 1, "
                + "`title` VARCHAR, "
                + "`description` VARCHAR, "
                + "`image_ios_600_url` VARCHAR"
                + ");";
        database.execSQL(CREATE_CHALLENGES);
    }
}
