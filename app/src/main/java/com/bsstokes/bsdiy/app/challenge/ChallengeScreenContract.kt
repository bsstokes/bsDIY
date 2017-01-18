package com.bsstokes.bsdiy.app.challenge

interface ChallengeScreenContract {

    interface View {
        fun setTitle(title: String)
        fun loadPatchImage(patchImageUrl: String)
        fun setChallengeTitle(title: String)
        fun setDescription(description: String)
    }

    interface Controller {
        fun start()
        fun stop()
    }
}