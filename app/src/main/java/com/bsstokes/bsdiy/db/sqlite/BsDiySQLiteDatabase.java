package com.bsstokes.bsdiy.db.sqlite;

import android.support.annotation.NonNull;

import com.bsstokes.bsdiy.api.DiyApi;
import com.bsstokes.bsdiy.db.BsDiyDatabase;
import com.bsstokes.bsdiy.db.sqlite.mappers.ChallengeMapper;
import com.bsstokes.bsdiy.db.sqlite.mappers.SkillMapper;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import rx.Observable;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;
import static com.bsstokes.bsdiy.db.sqlite.mappers.ChallengeMapper.ChallengeToContentValues.createChallenge;
import static com.bsstokes.bsdiy.db.sqlite.mappers.SkillMapper.SkillToContentValues.createSkill;

public class BsDiySQLiteDatabase implements BsDiyDatabase {

    private final BriteDatabase briteDatabase;

    public BsDiySQLiteDatabase(@NonNull BriteDatabase briteDatabase) {
        this.briteDatabase = briteDatabase;
    }

    @Override
    public Observable<List<DiyApi.Skill>> getAllSkills() {
        return briteDatabase.createQuery("skills", "SELECT * FROM skills WHERE active=1 ORDER BY priority DESC, title ASC")
                .mapToList(new SkillMapper.CursorToSkill());
    }

    @Override
    public Observable<DiyApi.Skill> getSkill(long skillId) {
        final String skillIdString = String.valueOf(skillId);
        return briteDatabase.createQuery("skills", "SELECT * FROM skills WHERE _id = ? LIMIT 1", skillIdString)
                .mapToOneOrDefault(new SkillMapper.CursorToSkill(), null);
    }

    @Override
    public Observable<DiyApi.Skill> getSkillByUrl(@NonNull String skillUrl) {
        return briteDatabase.createQuery("skills", "SELECT * FROM skills WHERE url = ? LIMIT 1", skillUrl)
                .mapToOneOrDefault(new SkillMapper.CursorToSkill(), null);
    }

    @Override
    public void putSkill(@NonNull DiyApi.Skill skill) {
        briteDatabase.insert("skills", createSkill(skill), CONFLICT_REPLACE);
    }

    @Override
    public void putSkills(List<DiyApi.Skill> skills) {
        final BriteDatabase.Transaction transaction = briteDatabase.newTransaction();
        try {
            for (final DiyApi.Skill skill : skills) {
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
