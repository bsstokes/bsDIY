package com.bsstokes.bsdiy.db.sqlite;

import android.support.annotation.NonNull;

import com.bsstokes.bsdiy.api.DiyApi;
import com.bsstokes.bsdiy.db.BsDiyDatabase;
import com.bsstokes.bsdiy.db.sqlite.mappers.SkillMapper;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import rx.Observable;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;
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
                .mapToOne(new SkillMapper.CursorToSkill());
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
}
