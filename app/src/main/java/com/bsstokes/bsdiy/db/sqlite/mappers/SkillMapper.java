package com.bsstokes.bsdiy.db.sqlite.mappers;

import android.database.Cursor;

import com.bsstokes.bsdiy.api.DiyApi;
import com.bsstokes.bsdiy.db.sqlite.Db;

import rx.functions.Func1;

public class SkillMapper implements Func1<Cursor, DiyApi.Skill> {
    @Override
    public DiyApi.Skill call(Cursor cursor) {
        final DiyApi.Skill skill = new DiyApi.Skill();
        skill.id = Db.getLong(cursor, "_id");
        skill.active = Db.getBoolean(cursor, "active");
        skill.url = Db.getString(cursor, "url");
        skill.title = Db.getString(cursor, "title");
        skill.description = Db.getString(cursor, "description");
        skill.color = Db.getString(cursor, "color");
        return skill;
    }
}
