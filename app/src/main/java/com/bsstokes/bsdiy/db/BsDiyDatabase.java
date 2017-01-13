package com.bsstokes.bsdiy.db;

import android.support.annotation.NonNull;

import com.bsstokes.bsdiy.api.DiyApi;

import java.util.List;

import rx.Observable;

public interface BsDiyDatabase {
    Observable<List<DiyApi.Skill>> getAllSkills();

    Observable<DiyApi.Skill> getSkill(long skillId);

    Observable<DiyApi.Skill> getSkillByUrl(@NonNull String skillUrl);

    void putSkill(DiyApi.Skill skill);

    void putSkills(List<DiyApi.Skill> skills);

    void putChallenge(DiyApi.Challenge challenge, long skillId);

    void putChallenges(List<DiyApi.Challenge> challenges, long skillId);
}
