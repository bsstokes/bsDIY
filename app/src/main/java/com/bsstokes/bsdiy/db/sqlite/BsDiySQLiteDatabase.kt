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
        val query = """
                SELECT *
                FROM ${SkillMapping.Table.NAME}
                WHERE ${SkillMapping.Columns.ACTIVE}=1
                ORDER BY
                    ${SkillMapping.Columns.PRIORITY} DESC,
                    ${SkillMapping.Columns.TITLE} ASC
                """.trimIndent()
        return briteDatabase.createQuery(SkillMapping.Table.NAME, query)
                .mapToList<Skill>(SkillMapping.MAPPER)
    }

    override fun getSkill(skillId: Long): Observable<Skill?> {
        val query = """
                SELECT *
                FROM ${SkillMapping.Table.NAME}
                WHERE ${SkillMapping.Columns.ID}=?
                LIMIT 1
                """.trimIndent()
        return briteDatabase.createQuery(SkillMapping.Table.NAME, query, skillId.toString())
                .mapToOneOrDefault<Skill>(SkillMapping.MAPPER, null)
    }

    override fun getSkillByUrl(skillUrl: String): Observable<Skill?> {
        val query = """
                SELECT *
                FROM ${SkillMapping.Table.NAME}
                WHERE ${SkillMapping.Columns.URL}=?
                LIMIT 1
                """.trimIndent()
        return briteDatabase.createQuery(SkillMapping.Table.NAME, query, skillUrl)
                .mapToOneOrDefault<Skill>(SkillMapping.MAPPER, null)
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
        val query = """
                SELECT *
                FROM ${ChallengeMapping.Table.NAME}
                WHERE ${ChallengeMapping.Columns.SKILL_ID}=?
                AND ${ChallengeMapping.Columns.ACTIVE}=1
                ORDER BY
                    ${ChallengeMapping.Columns.POSITION} ASC,
                    ${ChallengeMapping.Columns.SKILL_ID} ASC
                """.trimIndent()
        return briteDatabase.createQuery(ChallengeMapping.Table.NAME, query, skillId.toString())
                .mapToList<Challenge>(ChallengeMapping.MAPPER)
    }

    override fun getChallenge(challengeId: Long): Observable<Challenge?> {
        val query = """
                SELECT *
                FROM ${ChallengeMapping.Table.NAME}
                WHERE ${ChallengeMapping.Columns.ID}=?
                LIMIT 1
                """.trimIndent()
        return briteDatabase.createQuery(ChallengeMapping.Table.NAME, query, challengeId.toString())
                .mapToOneOrDefault<Challenge>(ChallengeMapping.MAPPER, null)
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
