package com.bsstokes.bsdiy.db.sqlite.mappers;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.bsstokes.bsdiy.api.DiyApi;
import com.bsstokes.bsdiy.db.sqlite.Db;

import rx.functions.Func1;

public interface SkillMapper {
    class CursorToSkill implements Func1<Cursor, DiyApi.Skill> {
        @Override
        public DiyApi.Skill call(Cursor cursor) {
            final DiyApi.Skill skill = new DiyApi.Skill();
            skill.id = Db.getLong(cursor, "_id");
            skill.active = Db.getBoolean(cursor, "active");
            skill.url = Db.getString(cursor, "url");
            skill.title = Db.getString(cursor, "title");
            skill.description = Db.getString(cursor, "description");
            skill.icons = new DiyApi.Skill.Icons();
            skill.icons.small = Db.getString(cursor, "icon_small");
            skill.icons.medium = Db.getString(cursor, "icon_medium");
            skill.images = new DiyApi.Skill.Images(
                    Db.getString(cursor, "image_small"),
                    Db.getString(cursor, "image_medium"),
                    Db.getString(cursor, "image_large")
            );
            skill.color = Db.getString(cursor, "color");
            return skill;
        }
    }

    class SkillToContentValues implements Func1<DiyApi.Skill, ContentValues> {

        private static SkillToContentValues INSTANCE = new SkillToContentValues();

        @NonNull
        public static ContentValues createSkill(@NonNull DiyApi.Skill skill) {
            return INSTANCE.call(skill);
        }

        @NonNull
        @Override
        public ContentValues call(@NonNull DiyApi.Skill skill) {
            final ContentValues contentValues = new ContentValues();
            contentValues.put("_id", skill.id);
            contentValues.put("active", skill.active);
            contentValues.put("url", skill.url);
            contentValues.put("title", skill.title);
            contentValues.put("description", skill.description);
            contentValues.put("color", skill.color);

            final DiyApi.Skill.Icons icons = skill.icons;
            if (null != icons) {
                contentValues.put("icon_small", icons.small);
                contentValues.put("icon_medium", icons.medium);
            }

            final DiyApi.Skill.Images images = skill.images;
            if (null != images) {
                contentValues.put("image_small", images.small);
                contentValues.put("image_medium", images.medium);
                contentValues.put("image_large", images.large);
            }

            return contentValues;
        }
    }
}
