package com.bsstokes.bsdiy.sync

import com.bsstokes.bsdiy.api.DiyApi
import com.bsstokes.bsdiy.db.Challenge
import com.bsstokes.bsdiy.db.Skill

fun apiChallengeToChallenge(apiChallenge: DiyApi.Challenge, skillId: Long, position: Int): Challenge {
    return apiChallenge.run {
        Challenge(
                id = id,
                skillId = skillId,
                position = position,
                active = active,
                title = title,
                description = description,
                imageIos600Url = image?.ios_600?.url,
                imageIos600Mime = image?.ios_600?.mime,
                imageIos600Width = image?.ios_600?.width,
                imageIos600Height = image?.ios_600?.height
        )
    }
}

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
