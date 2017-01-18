package com.bsstokes.bsdiy.db.sqlite

import android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE
import com.bsstokes.bsdiy.db.BsDiyDatabase
import com.bsstokes.bsdiy.db.Challenge
import com.bsstokes.bsdiy.db.Skill
import com.bsstokes.bsdiy.db.sqlite.mappers.ChallengeMapping
import com.bsstokes.bsdiy.db.sqlite.mappers.SkillMapping
import com.bsstokes.bsdiy.db.sqlite.mappers.toContentValues
import com.squareup.sqlbrite.BriteDatabase
import rx.Observable

class BsDiySQLiteDatabase(private val briteDatabase: BriteDatabase) : BsDiyDatabase {

    override fun getAllSkills(): Observable<List<Skill>> {
        return briteDatabase.createQuery(SkillMapping.Table.NAME, """
                SELECT * FROM ${SkillMapping.Table.NAME} WHERE ${SkillMapping.Columns.ACTIVE}=1 ORDER BY ${SkillMapping.Columns.PRIORITY} DESC, ${SkillMapping.Columns.TITLE} ASC
                """.trimIndent()
        ).mapToList<Skill>(SkillMapping.M)
    }

    override fun getSkill(skillId: Long): Observable<Skill?> {
        val skillIdString = skillId.toString()
        return briteDatabase.createQuery(SkillMapping.Table.NAME, """
                SELECT * FROM ${SkillMapping.Table.NAME} WHERE ${SkillMapping.Columns.ID}=? LIMIT 1
                """.trimIndent(),
                skillIdString
        ).mapToOneOrDefault<Skill>(SkillMapping.M, null)
    }

    override fun getSkillByUrl(skillUrl: String): Observable<Skill?> {
        return briteDatabase.createQuery(SkillMapping.Table.NAME, """
                SELECT * FROM ${SkillMapping.Table.NAME} WHERE ${SkillMapping.Columns.URL}=? LIMIT 1
                """.trimIndent(),
                skillUrl
        ).mapToOneOrDefault<Skill>(SkillMapping.M, null)
    }

    override fun putSkill(skill: Skill) {
        briteDatabase.insert(SkillMapping.Table.NAME, skill.toContentValues(), CONFLICT_REPLACE)
    }

    override fun putSkills(skills: List<Skill>) {
        briteDatabase.transaction {
            skills.forEach { putSkill(it) }
        }
    }

    override fun getChallenges(skillId: Long): Observable<List<Challenge>> {
        val skillIdString = skillId.toString()
        return briteDatabase.createQuery(ChallengeMapping.Table.NAME, """
                SELECT * FROM ${ChallengeMapping.Table.NAME}
                WHERE ${ChallengeMapping.Columns.SKILL_ID}=? AND ${ChallengeMapping.Columns.ACTIVE}=1
                ORDER BY ${ChallengeMapping.Columns.POSITION} ASC, ${ChallengeMapping.Columns.SKILL_ID} ASC
                """.trimIndent(),
                skillIdString
        ).mapToList<Challenge>(ChallengeMapping.M)
    }

    override fun getChallenge(challengeId: Long): Observable<Challenge?> {
        val challengeIdString = challengeId.toString()
        return briteDatabase.createQuery(ChallengeMapping.Table.NAME, """
                SELECT * FROM ${ChallengeMapping.Table.NAME} WHERE ${ChallengeMapping.Columns.ID}=? LIMIT 1
                """.trimIndent(),
                challengeIdString
        ).mapToOneOrDefault<Challenge>(ChallengeMapping.M, null)
    }

    override fun putChallenge(challenge: Challenge) {
        briteDatabase.insert(ChallengeMapping.Table.NAME, challenge.toContentValues(), CONFLICT_REPLACE)
    }

    override fun putChallenges(challenges: List<Challenge>) {
        briteDatabase.transaction {
            challenges.forEach { putChallenge(it) }
        }
    }

    private fun BriteDatabase.transaction(block: () -> Unit) {
        val transaction = newTransaction()
        try {
            block()
            transaction.markSuccessful()
        } finally {
            transaction.end()
        }
    }
}
