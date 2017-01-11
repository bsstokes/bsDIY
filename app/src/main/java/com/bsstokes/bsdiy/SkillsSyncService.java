package com.bsstokes.bsdiy;

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

public class SkillsSyncService extends IntentService {

    public static void startActionSyncSkills(@NonNull Context context) {
        final Intent intent = new Intent(context, SkillsSyncService.class);
        intent.setAction(ACTION_SYNC_SKILLS);
        context.startService(intent);
    }

    private static final String ACTION_SYNC_SKILLS = "com.bsstokes.bsdiy.action.SYNC_SKILLS";

    @Inject DiyApi diyApi;
    @Inject BsDiyDatabase database;

    public SkillsSyncService() {
        super("SkillsSyncService");
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
            }
        }
    }

    private void handleActionSyncSkills() {
        SkillsDownloader skillsDownloader = new SkillsDownloader(diyApi, database);
        skillsDownloader.syncSkills();
    }
}
