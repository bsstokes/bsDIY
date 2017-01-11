package com.bsstokes.bsdiy.db.sqlite;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.bsstokes.bsdiy.api.DiyApi;
import com.bsstokes.bsdiy.db.BsDiyDatabase;
import com.bsstokes.bsdiy.db.sqlite.mappers.SkillMapper;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import rx.Observable;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;

public class BsDiySQLiteDatabase implements BsDiyDatabase {

    private final BriteDatabase briteDatabase;

    public BsDiySQLiteDatabase(@NonNull BriteDatabase briteDatabase) {
        this.briteDatabase = briteDatabase;
    }

    @Override
    public Observable<List<DiyApi.Skill>> getAllSkills() {
        return briteDatabase.createQuery("skills", "SELECT * FROM skills")
                .mapToList(new SkillMapper());
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

        final DiyApi.Skill.Images images = skill.images;
        if (null != images) {
            contentValues.put("image_small", images.small);
            contentValues.put("image_medium", images.medium);
            contentValues.put("image_large", images.large);
        }

        return contentValues;
    }
}
