package com.bsstokes.bsdiy.app.skill

interface SkillScreenContract {
    interface View {
        fun showErrorAndFinish(message: String)
        fun setTitle(title: String)
        fun setDescription(description: String)
        fun loadPatchImage(patchImageUrl: String)
        fun clearChallengeViews()
        fun loadChallengeView(challengeId: Long, title: String, heroImageUrl: String?);
        fun launchChallengeActivity(skillId: Long, challengeId: Long)
        fun startSyncChallengesService(skillUrl: String)
    }

    interface Controller {
        fun start()
        fun stop()
        fun onClickChallenge(challengeId: Long)
    }
}