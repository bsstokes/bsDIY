package com.bsstokes.bsdiy.db.sqlite.mappers;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.bsstokes.bsdiy.api.DiyApi;
import com.bsstokes.bsdiy.db.sqlite.Db;

import rx.functions.Func1;

public interface ChallengeMapper {

    class CursorToChallenge implements Func1<Cursor, DiyApi.Challenge> {
        @Override
        public DiyApi.Challenge call(Cursor cursor) {
            final DiyApi.Challenge challenge = new DiyApi.Challenge();
            challenge.id = Db.getLong(cursor, "_id");
            challenge.active = Db.getBoolean(cursor, "active");
            challenge.title = Db.getString(cursor, "title");
            challenge.description = Db.getString(cursor, "description");
            challenge.image = new DiyApi.Challenge.Image();
            challenge.image.ios_600 = new DiyApi.Asset();
            challenge.image.ios_600.url = Db.getString(cursor, "image_ios_600_url");
            challenge.image.ios_600.mime = Db.getString(cursor, "image_ios_600_mime");
            challenge.image.ios_600.width = Db.getInt(cursor, "image_ios_600_width");
            challenge.image.ios_600.height = Db.getInt(cursor, "image_ios_600_height");
            return challenge;
        }
    }

    class ChallengeToContentValues implements Func1<DiyApi.Challenge, ContentValues> {

        private static ChallengeMapper.ChallengeToContentValues INSTANCE = new ChallengeToContentValues();

        @NonNull
        public static ContentValues createChallenge(@NonNull DiyApi.Challenge challenge, long skillId, int position) {
            final ContentValues contentValues = INSTANCE.call(challenge);
            contentValues.put("skill_id", skillId);
            contentValues.put("position", position);
            return contentValues;
        }

        @NonNull
        @Override
        public ContentValues call(@NonNull DiyApi.Challenge challenge) {
            final ContentValues contentValues = new ContentValues();

            contentValues.put("_id", challenge.id);
            // TODO: Figure out a way to keep this here
//            contentValues.put("skill_id", challenge.skill_id);
            contentValues.put("active", challenge.active);
            contentValues.put("title", challenge.title);
            contentValues.put("description", challenge.description);

            final DiyApi.Challenge.Image image = challenge.image;
            if (null != image && null != image.ios_600 && null != image.ios_600.url) {
                contentValues.put("image_ios_600_url", image.ios_600.url);
                contentValues.put("image_ios_600_mime", image.ios_600.mime);
                contentValues.put("image_ios_600_width", image.ios_600.width);
                contentValues.put("image_ios_600_height", image.ios_600.height);
            }

            return contentValues;
        }
    }
}
