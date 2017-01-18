package com.bsstokes.bsdiy.db.sqlite.migrations

import android.database.sqlite.SQLiteDatabase

interface Migration {
    fun onUpgrade(database: SQLiteDatabase)
}
