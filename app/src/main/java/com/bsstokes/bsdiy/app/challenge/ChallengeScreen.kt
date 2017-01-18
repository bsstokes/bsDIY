package com.bsstokes.bsdiy.app.challenge

import com.bsstokes.bsdiy.api.DiyApi
import com.bsstokes.bsdiy.db.BsDiyDatabase
import com.bsstokes.bsdiy.db.Challenge
import com.bsstokes.bsdiy.db.Skill
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class ChallengeScreen(private val skillId: Long,
                      private val challengeId: Long,
                      private val database: BsDiyDatabase,
                      private val view: ChallengeScreenContract.View)
    : ChallengeScreenContract.Controller {

    private val subscriptions = CompositeSubscription()

    override fun start() {
        val getSkill = database.getSkill(skillId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { skill -> onLoadSkill(skill) }
        subscriptions.add(getSkill)

        val getChallenge = database.getChallenge(challengeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { challenge -> onLoadChallenge(challenge) }
        subscriptions.add(getChallenge)
    }

    override fun stop() {
        subscriptions.clear()
    }

    private fun onLoadSkill(skill: Skill?) {
        skill?.iconMedium?.let {
            view.loadPatchImage(DiyApi.Helper.normalizeUrl(skill.iconMedium))
        }
    }

    private fun onLoadChallenge(challenge: Challenge?) {
        view.setTitle("")
        view.setChallengeTitle(challenge?.title ?: "")
        view.setDescription(challenge?.description ?: "")
    }
}