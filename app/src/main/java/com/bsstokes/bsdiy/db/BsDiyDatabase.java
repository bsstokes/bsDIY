package com.bsstokes.bsdiy.db;

import com.bsstokes.bsdiy.api.DiyApi;
import com.squareup.sqlbrite.QueryObservable;

public interface BsDiyDatabase {
    QueryObservable getAllSkills();

    void putSkill(DiyApi.Skill skill);
}
