package com.bsstokes.bsdiy.db.sqlite.mappers

import android.content.ContentValues
import android.database.Cursor
import com.bsstokes.bsdiy.db.Challenge
import rx.functions.Func1

class ChallengeMapping {
    object Table {
        const val NAME = "challenges"
    }

    object Columns {
        const val ID = "_id"
        const val SKILL_ID = "skill_id"
        const val ACTIVE = "active"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val IMAGE_IOS_600_URL = "image_ios_600_url"
        const val IMAGE_IOS_600_MIME = "image_ios_600_mime"
        const val IMAGE_IOS_600_WIDTH = "image_ios_600_width"
        const val IMAGE_IOS_600_HEIGHT = "image_ios_600_height"
        const val POSITION = "position"
    }

    object MAPPER : Func1<Cursor, Challenge?> {
        override fun call(t: Cursor?): Challenge? {
            return t?.toChallenge()
        }
    }
}

fun Challenge.toContentValues(): ContentValues {
    return ContentValues().apply {
        put(ChallengeMapping.Columns.ID, id)
        put(ChallengeMapping.Columns.SKILL_ID, skillId)
        put(ChallengeMapping.Columns.ACTIVE, active)
        put(ChallengeMapping.Columns.TITLE, title)
        put(ChallengeMapping.Columns.DESCRIPTION, description)
        put(ChallengeMapping.Columns.IMAGE_IOS_600_URL, imageIos600Url)
        put(ChallengeMapping.Columns.IMAGE_IOS_600_MIME, imageIos600Mime)
        put(ChallengeMapping.Columns.IMAGE_IOS_600_WIDTH, imageIos600Width)
        put(ChallengeMapping.Columns.IMAGE_IOS_600_HEIGHT, imageIos600Height)
        put(ChallengeMapping.Columns.POSITION, position)
    }
}

fun Cursor.toChallenge(): Challenge {
    return Challenge(
            id = getLong(ChallengeMapping.Columns.ID),
            skillId = getLong(ChallengeMapping.Columns.SKILL_ID),
            active = getBoolean(ChallengeMapping.Columns.ACTIVE),
            title = getString(ChallengeMapping.Columns.TITLE),
            description = getString(ChallengeMapping.Columns.DESCRIPTION),
            imageIos600Url = getString(ChallengeMapping.Columns.IMAGE_IOS_600_URL),
            imageIos600Mime = getString(ChallengeMapping.Columns.IMAGE_IOS_600_MIME),
            imageIos600Width = getInt(ChallengeMapping.Columns.IMAGE_IOS_600_WIDTH),
            imageIos600Height = getInt(ChallengeMapping.Columns.IMAGE_IOS_600_HEIGHT),
            position = getInt(ChallengeMapping.Columns.POSITION)
    )
}
