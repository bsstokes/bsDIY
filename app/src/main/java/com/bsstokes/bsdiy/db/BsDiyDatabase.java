package com.bsstokes.bsdiy.db;

import android.support.annotation.NonNull;

import com.bsstokes.bsdiy.api.DiyApi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import rx.Observable;

public interface BsDiyDatabase {
    Observable<List<Skill>> getAllSkills();

    Observable<Skill> getSkill(long skillId);

    Observable<Skill> getSkillByUrl(@NonNull String skillUrl);

    void putSkill(Skill skill);

    void putSkills(@NotNull List<Skill> skills);

    Observable<List<Challenge>> getChallenges(long skillId);

    Observable<Challenge> getDbChallenge(long challengeId);

    Observable<DiyApi.Challenge> getChallenge(long challengeId);

    void putChallenge(@NotNull Challenge challenge);

    void putChallenge(DiyApi.Challenge challenge, long skillId, int position);

    void putChallenges(List<DiyApi.Challenge> challenges, long skillId);
}
