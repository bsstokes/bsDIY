package com.bsstokes.bsdiy.app.skill

import com.bsstokes.bsdiy.db.BsDiyDatabase
import com.bsstokes.bsdiy.db.Challenge
import com.bsstokes.bsdiy.db.Skill
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class SkillLoader(private val skillId: Long,
                  private val database: BsDiyDatabase,
                  private val listener: Listener) {

    interface Listener {
        fun onLoadSkill(skill: Skill?)
        fun onLoadChallenges(challenges: List<Challenge>)
    }

    private val subscriptions = CompositeSubscription()

    fun start() {
        val getSkill = database.getSkill(skillId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { skill: Skill? -> listener.onLoadSkill(skill) }
        subscriptions.add(getSkill)

        val getChallenges = database.getChallenges(skillId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { challenges: List<Challenge> -> listener.onLoadChallenges(challenges) }
        subscriptions.add(getChallenges)
    }

    fun stop() {
        subscriptions.clear()
    }
}