package com.bsstokes.bsdiy.db.sqlite.mappers

import android.content.ContentValues
import android.database.Cursor
import com.bsstokes.bsdiy.db.Skill
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

    object MAPPER : Func1<Cursor, Skill?> {
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
            id = getLong(SkillMapping.Columns.ID),
            active = getBoolean(SkillMapping.Columns.ACTIVE),
            url = getString(SkillMapping.Columns.URL),
            title = getString(SkillMapping.Columns.TITLE),
            description = getString(SkillMapping.Columns.DESCRIPTION),
            color = getString(SkillMapping.Columns.COLOR),
            iconSmall = getString(SkillMapping.Columns.ICON_SMALL),
            iconMedium = getString(SkillMapping.Columns.ICON_MEDIUM),
            imageSmall = getString(SkillMapping.Columns.IMAGE_SMALL),
            imageMedium = getString(SkillMapping.Columns.IMAGE_MEDIUM),
            imageLarge = getString(SkillMapping.Columns.IMAGE_LARGE)
    )
}
