package com.bsstokes.bsdiy.sync

import android.util.Log
import com.bsstokes.bsdiy.api.DiyApi
import com.bsstokes.bsdiy.db.BsDiyDatabase
import com.bsstokes.bsdiy.db.Skill
import com.bsstokes.bsdiy.sync.mappers.SkillMapping.Companion.toSkill
import retrofit2.Response
import rx.Observer

internal class SkillsDownloader(private val diyApi: DiyApi, private val database: BsDiyDatabase) {

    fun syncSkills() {
        diyApi.skills.subscribe(object : Observer<Response<DiyApi.DiyResponse<List<DiyApi.Skill>>>> {
            private val TAG = "DiyApi getSkills"

            override fun onNext(response: Response<DiyApi.DiyResponse<List<DiyApi.Skill>>>) {
                onDownloadSkillsResponse(response)
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError: diyApi getSkills", e)
            }

            override fun onCompleted() {
                Log.d(TAG, "onCompleted")
            }
        })
    }

    private fun onDownloadSkillsResponse(response: Response<DiyApi.DiyResponse<List<DiyApi.Skill>>>?) {
        response?.body()?.let {
            onDownloadSkillsResponse(it)
        }
    }

    private fun onDownloadSkillsResponse(skillsResponse: DiyApi.DiyResponse<List<DiyApi.Skill>>) {
        skillsResponse.response?.let {
            onDownloadSkills(it)
        }
    }

    private fun onDownloadSkills(apiSkills: List<DiyApi.Skill>) {
        val skills: List<Skill> = apiSkills.map { apiSkill -> apiSkill.toSkill() }
        database.putSkills(skills)
    }
}
