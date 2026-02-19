package com.nativkod.schoolnoticeboard.data.mapper

import com.nativkod.schoolnoticeboard.data.local.entity.NoticeEntity
import com.nativkod.schoolnoticeboard.data.remote.NoticeDto

object NoticeMapper {

    fun dtoToEntityOrNull(
        dto: NoticeDto,
        publishedAtEpochMillis: Long?,
        friendlyLocal: String
    ): NoticeEntity? {
        val id = dto.id?.trim().takeUnless { it.isNullOrBlank() } ?: return null
        val title = dto.title?.trim().takeUnless { it.isNullOrBlank() } ?: return null
        val body = dto.noticeBody?.trim().takeUnless { it.isNullOrBlank() } ?: return null
        val rawDate = dto.datePublished?.trim().takeUnless { it.isNullOrBlank() } ?: return null
        val epoch = publishedAtEpochMillis ?: return null

        return NoticeEntity(
            id = id,
            title = title,
            body = body,
            imageUrl = dto.imageUrl?.trim()?.takeIf { it.isNotBlank() },
            publishedAtEpochMillis = epoch,
            publishedAtRaw = rawDate,
            publishedAtFriendlyLocal = friendlyLocal
        )
    }
}
