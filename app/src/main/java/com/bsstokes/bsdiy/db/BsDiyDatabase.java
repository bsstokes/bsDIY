package com.bsstokes.bsdiy.db;

import com.bsstokes.bsdiy.api.DiyApi;

import java.util.List;

import rx.Observable;

public interface BsDiyDatabase {
    Observable<List<DiyApi.Skill>> getAllSkills();

    Observable<DiyApi.Skill> getSkill(long skillId);

    void putSkill(DiyApi.Skill skill);

    void putSkills(List<DiyApi.Skill> skills);
}
