package com.bsstokes.bsdiy.sync;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bsstokes.bsdiy.api.DiyApi;
import com.bsstokes.bsdiy.application.BsDiyApplication;
import com.bsstokes.bsdiy.db.BsDiyDatabase;

import javax.inject.Inject;

import dagger.internal.Preconditions;

public class ApiSyncService extends IntentService {

    public static void syncSkills(@NonNull Context context) {
        final Intent intent = new Intent(context, ApiSyncService.class);
        intent.setAction(ACTION_SYNC_SKILLS);
        context.startService(intent);
    }

    public static void syncChallenges(@NonNull Context context, @NonNull String skillUrl) {
        final Intent intent = new Intent(context, ApiSyncService.class);
        intent.setAction(ACTION_SYNC_CHALLENGES);
        intent.putExtra(EXTRA_SKILL_URL, skillUrl);
        context.startService(intent);
    }

    private static final String ACTION_SYNC_SKILLS = "com.bsstokes.bsdiy.action.SYNC_SKILLS";
    private static final String ACTION_SYNC_CHALLENGES = "com.bsstokes.bsdiy.action.SYNC_CHALLENGES";

    private static final String EXTRA_SKILL_URL = "skillUrl";

    @Inject DiyApi diyApi;
    @Inject BsDiyDatabase database;

    public ApiSyncService() {
        super("ApiSyncService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        BsDiyApplication.getApplication(this).appComponent().inject(this);
        Preconditions.checkNotNull(diyApi);
        Preconditions.checkNotNull(database);

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SYNC_SKILLS.equals(action)) {
                handleActionSyncSkills();
            } else if (ACTION_SYNC_CHALLENGES.equals(action)) {
                final String skillUrl = intent.getStringExtra(EXTRA_SKILL_URL);
                handleActionSyncChallenges(skillUrl);
            }
        }
    }

    private void handleActionSyncSkills() {
        final SkillsDownloader skillsDownloader = new SkillsDownloader(diyApi, database);
        skillsDownloader.syncSkills();
    }

    private void handleActionSyncChallenges(@Nullable String skillUrl) {
        if (null == skillUrl) {
            return;
        }

        final ChallengesDownloader challengesDownloader = new ChallengesDownloader(diyApi, database, skillUrl);
        challengesDownloader.syncChallenges();
    }
}
