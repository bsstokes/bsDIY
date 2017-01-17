package com.bsstokes.bsdiy.sync

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.bsstokes.bsdiy.api.DiyApi
import com.bsstokes.bsdiy.application.BsDiyApplication
import com.bsstokes.bsdiy.db.BsDiyDatabase
import javax.inject.Inject

class ApiSyncService : IntentService("ApiSyncService") {

    companion object {
        @JvmStatic
        fun syncSkills(context: Context) {
            val intent = Intent(context, ApiSyncService::class.java)
            intent.action = ACTION_SYNC_SKILLS
            context.startService(intent)
        }

        @JvmStatic
        fun syncChallenges(context: Context, skillUrl: String) {
            val intent = Intent(context, ApiSyncService::class.java)
            intent.action = ACTION_SYNC_CHALLENGES
            intent.putExtra(EXTRA_SKILL_URL, skillUrl)
            context.startService(intent)
        }

        private const val ACTION_SYNC_SKILLS = "com.bsstokes.bsdiy.action.SYNC_SKILLS"
        private const val ACTION_SYNC_CHALLENGES = "com.bsstokes.bsdiy.action.SYNC_CHALLENGES"

        private const val EXTRA_SKILL_URL = "skillUrl"
    }

    @Inject internal lateinit var diyApi: DiyApi
    @Inject internal lateinit var database: BsDiyDatabase

    override fun onHandleIntent(intent: Intent?) {
        BsDiyApplication.getApplication(this).appComponent().inject(this)
        requireNotNull(diyApi)
        requireNotNull(database)

        intent?.let {
            val action = intent.action
            if (ACTION_SYNC_SKILLS == action) {
                handleActionSyncSkills()
            } else if (ACTION_SYNC_CHALLENGES == action) {
                val skillUrl = intent.getStringExtra(EXTRA_SKILL_URL)
                handleActionSyncChallenges(skillUrl)
            }
        }
    }

    private fun handleActionSyncSkills() {
        val skillsDownloader = SkillsDownloader(diyApi, database)
        skillsDownloader.syncSkills()
    }

    private fun handleActionSyncChallenges(skillUrl: String?) {
        skillUrl?.let {
            val challengesDownloader = ChallengesDownloader(diyApi, database, skillUrl)
            challengesDownloader.syncChallenges()
        }
    }
}
