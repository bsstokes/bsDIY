package com.bsstokes.bsdiy.db.sqlite.mappers

import android.content.ContentValues
import android.database.Cursor
import com.bsstokes.bsdiy.db.Skill
import com.bsstokes.bsdiy.db.sqlite.Db
import rx.functions.Func1

class SkillMapping {
    object Table {
        const val NAME = "skills"
    }

    object Columns {
        const val ID = "_id"
        const val ACTIVE = "active"
        const val URL = "url"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val COLOR = "color"
        const val ICON_SMALL = "icon_small"
        const val ICON_MEDIUM = "icon_medium"
        const val IMAGE_SMALL = "image_small"
        const val IMAGE_MEDIUM = "image_medium"
        const val IMAGE_LARGE = "image_large"
        const val PRIORITY = "priority"
    }

    object M : Func1<Cursor, Skill?> {
        override fun call(t: Cursor?): Skill? {
            return t?.toSkill()
        }
    }
}

fun Skill.toContentValues(): ContentValues {
    return ContentValues().apply {
        put(SkillMapping.Columns.ID, id)
        put(SkillMapping.Columns.ACTIVE, active)
        put(SkillMapping.Columns.URL, url)
        put(SkillMapping.Columns.TITLE, title)
        put(SkillMapping.Columns.DESCRIPTION, description)
        put(SkillMapping.Columns.COLOR, color)
        put(SkillMapping.Columns.ICON_SMALL, iconSmall)
        put(SkillMapping.Columns.ICON_MEDIUM, iconMedium)
        put(SkillMapping.Columns.IMAGE_SMALL, imageSmall)
        put(SkillMapping.Columns.IMAGE_MEDIUM, imageMedium)
        put(SkillMapping.Columns.IMAGE_LARGE, imageLarge)
    }
}

fun Cursor.toSkill(): Skill {
    return Skill(
            id = Db.getLong(this, SkillMapping.Columns.ID),
            active = Db.getBoolean(this, SkillMapping.Columns.ACTIVE),
            url = Db.getString(this, SkillMapping.Columns.URL),
            title = Db.getString(this, SkillMapping.Columns.TITLE),
            description = Db.getString(this, SkillMapping.Columns.DESCRIPTION),
            color = Db.getString(this, SkillMapping.Columns.COLOR),
            iconSmall = Db.getString(this, SkillMapping.Columns.ICON_SMALL),
            iconMedium = Db.getString(this, SkillMapping.Columns.ICON_MEDIUM),
            imageSmall = Db.getString(this, SkillMapping.Columns.IMAGE_SMALL),
            imageMedium = Db.getString(this, SkillMapping.Columns.IMAGE_MEDIUM),
            imageLarge = Db.getString(this, SkillMapping.Columns.IMAGE_LARGE)
    )
}
