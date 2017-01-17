package com.bsstokes.bsdiy.db.sqlite;

import android.support.annotation.NonNull;

import com.bsstokes.bsdiy.api.DiyApi;
import com.bsstokes.bsdiy.db.BsDiyDatabase;
import com.bsstokes.bsdiy.db.Skill;
import com.bsstokes.bsdiy.db.sqlite.mappers.ChallengeMapper;
import com.bsstokes.bsdiy.db.sqlite.mappers.SkillMapping;
import com.bsstokes.bsdiy.db.sqlite.mappers.SkillMappingKt;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import rx.Observable;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;
import static com.bsstokes.bsdiy.db.sqlite.mappers.ChallengeMapper.ChallengeToContentValues.createChallenge;

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
    public Observable<List<DiyApi.Challenge>> getChallenges(long skillId) {
        final String skillIdString = String.valueOf(skillId);
        return briteDatabase.createQuery("challenges", "SELECT * FROM challenges WHERE skill_id = ? AND active=1 ORDER BY position ASC, _id ASC", skillIdString)
                .mapToList(new ChallengeMapper.CursorToChallenge());
    }

    @Override
    public Observable<DiyApi.Challenge> getChallenge(long challengeId) {
        final String challengeIdString = String.valueOf(challengeId);
        return briteDatabase.createQuery("challenges", "SELECT * FROM challenges WHERE _id = ? LIMIT 1", challengeIdString)
                .mapToOneOrDefault(new ChallengeMapper.CursorToChallenge(), null);
    }

    @Override
    public void putChallenge(DiyApi.Challenge challenge, long skillId, int position) {
        briteDatabase.insert("challenges", createChallenge(challenge, skillId, position), CONFLICT_REPLACE);
    }

    @Override
    public void putChallenges(List<DiyApi.Challenge> challenges, long skillId) {
        final BriteDatabase.Transaction transaction = briteDatabase.newTransaction();
        try {
            for (int position = 0; position < challenges.size(); position++) {
                final DiyApi.Challenge challenge = challenges.get(position);
                putChallenge(challenge, skillId, position);
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
    }
}
