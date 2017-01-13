package com.bsstokes.bsdiy;

import android.support.annotation.NonNull;
import android.util.Log;

import com.bsstokes.bsdiy.api.DiyApi;
import com.bsstokes.bsdiy.db.BsDiyDatabase;

import java.util.List;

import retrofit2.Response;
import rx.Observer;
import rx.schedulers.Schedulers;

class ChallengesDownloader {

    private static final String TAG = "ChallengesDownloader";

    private final @NonNull DiyApi diyApi;
    private final @NonNull BsDiyDatabase database;
    private final @NonNull String skillUrl;

    ChallengesDownloader(@NonNull DiyApi diyApi, @NonNull BsDiyDatabase database, @NonNull String skillUrl) {
        this.diyApi = diyApi;
        this.database = database;
        this.skillUrl = skillUrl;
    }

    void syncChallenges() {
        Log.d(TAG, "syncChallenges: thread=" + Thread.currentThread().getName());

        diyApi.getChallenges(skillUrl)
                .subscribe(new Observer<Response<DiyApi.DiyResponse<List<DiyApi.Challenge>>>>() {
                    private static final String TAG = "DiyApi getChallenges";

                    @Override
                    public void onNext(Response<DiyApi.DiyResponse<List<DiyApi.Challenge>>> response) {
                        Log.d(TAG, "onNext: thread=" + Thread.currentThread().getName());
                        onDownloadChallengesResponse(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: diyApi getChallenges", e);
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }
                });
    }

    private void onDownloadChallengesResponse(Response<DiyApi.DiyResponse<List<DiyApi.Challenge>>> response) {
        if (null != response && null != response.body()) {
            onDownloadSkillsResponse(response.body());
        }
    }

    private void onDownloadSkillsResponse(DiyApi.DiyResponse<List<DiyApi.Challenge>> challengesResponse) {
        if (null != challengesResponse && null != challengesResponse.response) {
            onDownloadChallenge(challengesResponse.response);
        }
    }

    private void onDownloadChallenge(List<DiyApi.Challenge> challenges) {
        final DiyApi.Skill skill = database.getSkillByUrl(skillUrl)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .toBlocking()
                .first();

        if (null == skill) {
            Log.e(TAG, "Couldn't find skill url=" + skillUrl);
            return;
        }

        if (null != challenges) {
            database.putChallenges(challenges, skill.id);
        }
    }
}
