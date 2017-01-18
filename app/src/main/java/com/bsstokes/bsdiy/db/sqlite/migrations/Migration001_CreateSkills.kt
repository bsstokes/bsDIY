package com.bsstokes.bsdiy.db.sqlite.migrations

import android.database.sqlite.SQLiteDatabase

class Migration001_CreateSkills : Migration {

    override fun onUpgrade(database: SQLiteDatabase) {
        val CREATE_SKILLS = """
            CREATE TABLE `skills` (
                `_id` BIGINT PRIMARY KEY,
                `active` SMALLINT DEFAULT 1,
                `url` VARCHAR,
                `title` VARCHAR,
                `description` VARCHAR,
                `image_small` VARCHAR,
                `image_medium` VARCHAR,
                `image_large` VARCHAR,
                `icon_small` VARCHAR,
                `icon_medium` VARCHAR,
                `color` VARCHAR,
                `priority` INTEGER
            );""".trimIndent()
        database.execSQL(CREATE_SKILLS)
    }
}
