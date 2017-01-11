package com.bsstokes.bsdiy.db;

import com.bsstokes.bsdiy.api.DiyApi;

import java.util.List;

import rx.Observable;

public interface BsDiyDatabase {
    Observable<List<DiyApi.Skill>> getAllSkills();

    void putSkill(DiyApi.Skill skill);
}
