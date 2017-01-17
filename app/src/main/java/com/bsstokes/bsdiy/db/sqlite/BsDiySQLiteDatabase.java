package com.bsstokes.bsdiy.db.sqlite;

import android.support.annotation.NonNull;

import com.bsstokes.bsdiy.db.BsDiyDatabase;
import com.bsstokes.bsdiy.db.Challenge;
import com.bsstokes.bsdiy.db.Skill;
import com.bsstokes.bsdiy.db.sqlite.mappers.ChallengeMapping;
import com.bsstokes.bsdiy.db.sqlite.mappers.ChallengeMappingKt;
import com.bsstokes.bsdiy.db.sqlite.mappers.SkillMapping;
import com.bsstokes.bsdiy.db.sqlite.mappers.SkillMappingKt;
import com.squareup.sqlbrite.BriteDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import rx.Observable;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;

public class BsDiySQLiteDatabase implements BsDiyDatabase {

    private final BriteDatabase briteDatabase;

    public BsDiySQLiteDatabase(@NonNull BriteDatabase briteDatabase) {
        this.briteDatabase = briteDatabase;
    }

    @Override
    public Observable<List<Skill>> getAllSkills() {
        return briteDatabase.createQuery(SkillMapping.Table.NAME, "SELECT * FROM skills WHERE active=1 ORDER BY priority DESC, title ASC")
                .mapToList(SkillMapping.M.INSTANCE);
    }

    @Override
    public Observable<Skill> getSkill(long skillId) {
        final String skillIdString = String.valueOf(skillId);
        return briteDatabase.createQuery(SkillMapping.Table.NAME, "SELECT * FROM skills WHERE _id = ? LIMIT 1", skillIdString)
                .mapToOneOrDefault(SkillMapping.M.INSTANCE, null);
    }

    @Override
    public Observable<Skill> getSkillByUrl(@NonNull String skillUrl) {
        return briteDatabase.createQuery(SkillMapping.Table.NAME, "SELECT * FROM skills WHERE url = ? LIMIT 1", skillUrl)
                .mapToOneOrDefault(SkillMapping.M.INSTANCE, null);
    }

    @Override
    public void putSkill(Skill skill) {
        briteDatabase.insert(SkillMapping.Table.NAME, SkillMappingKt.toContentValues(skill), CONFLICT_REPLACE);
    }

    @Override
    public void putSkills(@NonNull List<Skill> skills) {
        final BriteDatabase.Transaction transaction = briteDatabase.newTransaction();
        try {
            for (final Skill skill : skills) {
                putSkill(skill);
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
    }

    @Override
    public Observable<List<Challenge>> getChallenges(long skillId) {
        final String skillIdString = String.valueOf(skillId);
        return briteDatabase.createQuery(ChallengeMapping.Table.NAME, "SELECT * FROM challenges WHERE skill_id = ? AND active=1 ORDER BY position ASC, _id ASC", skillIdString)
                .mapToList(ChallengeMapping.M.INSTANCE);
    }

    @Override
    public Observable<Challenge> getChallenge(long challengeId) {
        final String challengeIdString = String.valueOf(challengeId);
        return briteDatabase.createQuery(ChallengeMapping.Table.NAME, "SELECT * FROM challenges WHERE _id = ? LIMIT 1", challengeIdString)
                .mapToOneOrDefault(ChallengeMapping.M.INSTANCE, null);
    }

    @Override
    public void putChallenge(@NotNull Challenge challenge) {
        briteDatabase.insert(ChallengeMapping.Table.NAME, ChallengeMappingKt.toContentValues(challenge), CONFLICT_REPLACE);
    }

    @Override
    public void putChallenges(List<Challenge> challenges) {
        final BriteDatabase.Transaction transaction = briteDatabase.newTransaction();
        try {
            for (int position = 0; position < challenges.size(); position++) {
                final Challenge challenge = challenges.get(position);
                putChallenge(challenge);
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
    }
}
