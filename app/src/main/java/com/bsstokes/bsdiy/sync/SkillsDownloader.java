package com.bsstokes.bsdiy.sync;

import android.support.annotation.NonNull;
import android.util.Log;

import com.bsstokes.bsdiy.api.DiyApi;
import com.bsstokes.bsdiy.db.BsDiyDatabase;

import java.util.List;

import retrofit2.Response;
import rx.Observer;

class SkillsDownloader {

    private static final String TAG = "SkillsDownloader";

    private final @NonNull DiyApi diyApi;
    private final @NonNull BsDiyDatabase database;

    SkillsDownloader(@NonNull DiyApi diyApi, @NonNull BsDiyDatabase database) {
        this.diyApi = diyApi;
        this.database = database;
    }

    void syncSkills() {
        Log.d(TAG, "syncSkills: thread=" + Thread.currentThread().getName());

        diyApi.getSkills()
                .subscribe(new Observer<Response<DiyApi.DiyResponse<List<DiyApi.Skill>>>>() {
                    private static final String TAG = "DiyApi getSkills";

                    @Override
                    public void onNext(Response<DiyApi.DiyResponse<List<DiyApi.Skill>>> response) {
                        Log.d(TAG, "onNext: thread=" + Thread.currentThread().getName());
                        onDownloadSkillsResponse(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: diyApi getSkills", e);
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }
                });
    }

    private void onDownloadSkillsResponse(Response<DiyApi.DiyResponse<List<DiyApi.Skill>>> response) {
        if (null != response && null != response.body()) {
            onDownloadSkillsResponse(response.body());
        }
    }

    private void onDownloadSkillsResponse(DiyApi.DiyResponse<List<DiyApi.Skill>> skillsResponse) {
        if (null != skillsResponse && null != skillsResponse.response) {
            onDownloadSkills(skillsResponse.response);
        }
    }

    private void onDownloadSkills(List<DiyApi.Skill> skills) {
        if (null != skills) {
            database.putSkills(skills);
        }
    }
}
