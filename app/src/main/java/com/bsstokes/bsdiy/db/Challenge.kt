package com.bsstokes.bsdiy.db

data class Challenge(
        val id: Long,
        val skillId: Long,
        val position: Int?,
        val active: Boolean?,
        val title: String?,
        val description: String?,
        val imageIos600Url: String?,
        val imageIos600Mime: String?,
        val imageIos600Width: Int?,
        val imageIos600Height: Int?
)