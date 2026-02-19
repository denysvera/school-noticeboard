package com.nativkod.schoolnoticeboard.domain.model

data class Notice(
    val id: String,
    val title: String,
    val body: String,
    val imageUrl: String?,
    val publishedAtEpochMillis: Long,
    val publishedAtRaw: String,
    val publishedAtFriendlyLocal: String
)
