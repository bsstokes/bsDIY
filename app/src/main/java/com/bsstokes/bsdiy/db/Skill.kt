package com.bsstokes.bsdiy.db

data class Skill(
        val id: Long,
        val active: Boolean?,
        val url: String?,
        val title: String?,
        val description: String?,
        val color: String?,
        val iconSmall: String?,
        val iconMedium: String?,
        val imageSmall: String?,
        val imageMedium: String?,
        val imageLarge: String?
)
