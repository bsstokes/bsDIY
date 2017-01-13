package com.bsstokes.bsdiy.db.sqlite.mappers;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.bsstokes.bsdiy.api.DiyApi;

import rx.functions.Func1;

public interface ChallengeMapper {

    class ChallengeToContentValues implements Func1<DiyApi.Challenge, ContentValues> {

        private static ChallengeMapper.ChallengeToContentValues INSTANCE = new ChallengeToContentValues();

        @NonNull
        public static ContentValues createChallenge(@NonNull DiyApi.Challenge challenge, long skillId) {
            final ContentValues contentValues = INSTANCE.call(challenge);
            contentValues.put("skill_id", skillId);
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
            }

            return contentValues;
        }
    }
}
