package com.bsstokes.bsdiy.skill

import com.bsstokes.bsdiy.api.DiyApi
import com.bsstokes.bsdiy.db.BsDiyDatabase
import com.bsstokes.bsdiy.db.Challenge
import com.bsstokes.bsdiy.db.Skill

class SkillScreen(private val skillId: Long,
                  database: BsDiyDatabase,
                  private val view: SkillScreenContract.View)

    : SkillLoader.Listener, SkillScreenContract.Controller {

    val skillLoader = SkillLoader(skillId, database, this)

    override fun start() {
        skillLoader.start()
    }

    override fun stop() {
        skillLoader.stop()
    }

    override fun onClickChallenge(challengeId: Long) {
        view.launchChallengeActivity(skillId, challengeId)
    }

    override fun onLoadSkill(skill: Skill?) {
        if (null == skill) {
            view.showErrorAndFinish("Couldn't find skill")
            return
        }

        skill.url?.let {
            view.startSyncChallengesService(skill.url)
        }

        view.setTitle(skill.title ?: "")
        skill.imageLarge?.let {
            val imageUrl = DiyApi.Helper.normalizeUrl(skill.imageLarge)
            view.loadPatchImage(imageUrl)
        }
        view.setDescription(skill.description ?: "")
    }

    override fun onLoadChallenges(challenges: List<Challenge>) {
        view.clearChallengeViews()
        challenges.forEach { view.loadChallengeView(it.id, it.title ?: "", it.imageIos600Url) }
    }
}