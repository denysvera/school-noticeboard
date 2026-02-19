package com.nativkod.schoolnoticeboard.data.mapper

import com.nativkod.schoolnoticeboard.data.local.entity.NoticeEntity
import com.nativkod.schoolnoticeboard.domain.model.Notice

fun NoticeEntity.toDomain(): Notice = Notice(
    id = id,
    title = title,
    body = body,
    imageUrl = imageUrl,
    publishedAtEpochMillis = publishedAtEpochMillis,
    publishedAtRaw = publishedAtRaw,
    publishedAtFriendlyLocal = publishedAtFriendlyLocal
)
