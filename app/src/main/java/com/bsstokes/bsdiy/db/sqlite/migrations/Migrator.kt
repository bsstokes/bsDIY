package com.bsstokes.bsdiy.db.sqlite.migrations

import android.database.sqlite.SQLiteDatabase

class Migrator(private val migrations: Array<Migration>) {

    fun migrate(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        for (version in oldVersion..newVersion - 1) {
            migrations[version].onUpgrade(database)
        }
    }

    val version: Int
        get() = migrations.size
}
