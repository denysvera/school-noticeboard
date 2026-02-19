package com.nativkod.schoolnoticeboard.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notices")
data class NoticeEntity(
    @PrimaryKey val id: String,
    val title: String,
    val body: String,
    val imageUrl: String?,
    val publishedAtEpochMillis: Long,
    val publishedAtRaw: String,
    val publishedAtFriendlyLocal: String
)
