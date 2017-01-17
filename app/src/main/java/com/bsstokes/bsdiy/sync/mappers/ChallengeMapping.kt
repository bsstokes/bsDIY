package com.bsstokes.bsdiy.sync.mappers

import com.bsstokes.bsdiy.api.DiyApi
import com.bsstokes.bsdiy.db.Challenge

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
