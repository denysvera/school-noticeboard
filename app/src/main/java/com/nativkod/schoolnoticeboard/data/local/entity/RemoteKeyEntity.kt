package com.nativkod.schoolnoticeboard.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey val noticeId: String,
    val prevKey: String?,
    val nextKey: String?
)