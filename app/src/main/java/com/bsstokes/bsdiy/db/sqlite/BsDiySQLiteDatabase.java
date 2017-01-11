package com.bsstokes.bsdiy.db.sqlite;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.bsstokes.bsdiy.api.DiyApi;
import com.bsstokes.bsdiy.db.BsDiyDatabase;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.QueryObservable;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;

public class BsDiySQLiteDatabase implements BsDiyDatabase {

    private final BriteDatabase briteDatabase;

    public BsDiySQLiteDatabase(@NonNull BriteDatabase briteDatabase) {
        this.briteDatabase = briteDatabase;
    }

    @Override
    public QueryObservable getAllSkills() {
        return briteDatabase.createQuery("skills", "SELECT * FROM skills");
    }

    @Override
    public void putSkill(@NonNull DiyApi.Skill skill) {
        briteDatabase.insert("skills", createSkill(skill), CONFLICT_REPLACE);
    }

    @NonNull
    private ContentValues createSkill(@NonNull DiyApi.Skill skill) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put("_id", skill.id);
        contentValues.put("active", skill.active);
        contentValues.put("url", skill.url);
        contentValues.put("title", skill.title);
        contentValues.put("description", skill.description);
        contentValues.put("color", skill.color);
        return contentValues;
    }
}
