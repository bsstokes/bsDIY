package com.bsstokes.bsdiy.db.sqlite.mappers

import android.database.Cursor

private val BOOLEAN_TRUE = 1

fun Cursor.getString(columnName: String): String? {
    return getString(getColumnIndexOrThrow(columnName))
}

fun Cursor.getBoolean(columnName: String): Boolean {
    return getInt(columnName) == BOOLEAN_TRUE
}

fun Cursor.getLong(columnName: String): Long {
    return getLong(getColumnIndexOrThrow(columnName))
}

fun Cursor.getInt(columnName: String): Int {
    return getInt(getColumnIndexOrThrow(columnName))
}