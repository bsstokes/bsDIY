package com.bsstokes.bsdiy.sync.mappers

import com.bsstokes.bsdiy.api.DiyApi
import com.bsstokes.bsdiy.db.Skill

fun apiSkillToSkill(apiSkill: DiyApi.Skill): Skill {
    return apiSkill.run {
        Skill(
                id = id,
                active = active,
                url = url,
                title = title,
                description = description,
                color = color,
                iconSmall = icons?.small,
                iconMedium = icons?.medium,
                imageSmall = images?.small,
                imageMedium = images?.medium,
                imageLarge = images?.large
        )
    }
}
