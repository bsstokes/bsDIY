package com.bsstokes.bsdiy.sync

import android.util.Log
import com.bsstokes.bsdiy.api.DiyApi
import com.bsstokes.bsdiy.db.BsDiyDatabase
import com.bsstokes.bsdiy.sync.apiChallengeToChallenge
import retrofit2.Response
import rx.Observer
import rx.schedulers.Schedulers

internal class ChallengesDownloader(private val diyApi: DiyApi, private val database: BsDiyDatabase, private val skillUrl: String) {

    companion object {
        private val TAG = "ChallengesDownloader"
    }

    fun syncChallenges() {
        diyApi.getChallenges(skillUrl).subscribe(object : Observer<Response<DiyApi.DiyResponse<List<DiyApi.Challenge>>>> {
            private val TAG = "DiyApi.getChallenges"

            override fun onNext(response: Response<DiyApi.DiyResponse<List<DiyApi.Challenge>>>) {
                onDownloadChallengesResponse(response)
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError: DiyApi.getChallenges($skillUrl)", e)
            }

            override fun onCompleted() {
                Log.d(TAG, "onCompleted")
            }
        })
    }

    private fun onDownloadChallengesResponse(response: Response<DiyApi.DiyResponse<List<DiyApi.Challenge>>>?) {
        response?.body()?.let {
            onDownloadSkillsResponse(it)
        }
    }

    private fun onDownloadSkillsResponse(challengesResponse: DiyApi.DiyResponse<List<DiyApi.Challenge>>) {
        challengesResponse.response?.let {
            onDownloadChallenge(it)
        }
    }

    private fun onDownloadChallenge(challenges: List<DiyApi.Challenge>) {
        val skill = database.getSkillByUrl(skillUrl)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .toBlocking()
                .first()

        if (null == skill) {
            Log.e(TAG, "Couldn't find skill url=" + skillUrl)
            return
        }

        saveChallenges(challenges, skill.id)
    }

    private fun saveChallenges(apiChallenges: List<DiyApi.Challenge>, skillId: Long) {
        val challenges = apiChallenges.mapIndexed { position, apiChallenge ->
            apiChallengeToChallenge(apiChallenge, skillId, position)
        }
        database.putChallenges(challenges)
    }
}
