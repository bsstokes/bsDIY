package com.bsstokes.bsdiy.db

import rx.Observable

interface BsDiyDatabase {
    fun getAllSkills(): Observable<List<Skill>>

    fun getSkill(skillId: Long): Observable<Skill?>

    fun getSkillByUrl(skillUrl: String): Observable<Skill?>

    fun putSkill(skill: Skill)

    fun putSkills(skills: List<Skill>)

    fun getChallenges(skillId: Long): Observable<List<Challenge>>

    fun getChallenge(challengeId: Long): Observable<Challenge?>

    fun putChallenge(challenge: Challenge)

    fun putChallenges(challenges: List<Challenge>)
}
