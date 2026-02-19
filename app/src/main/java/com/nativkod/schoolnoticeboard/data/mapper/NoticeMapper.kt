package com.nativkod.schoolnoticeboard.data.mapper

import com.nativkod.schoolnoticeboard.data.local.entity.NoticeEntity
import com.nativkod.schoolnoticeboard.data.remote.NoticeDto
import com.nativkod.schoolnoticeboard.domain.model.Notice

object NoticeMapper {

    /**
     * Network DTO -> Database Entity
     */
    fun dtoToEntityOrNull(
        dto: NoticeDto,
        epochMillis: Long,
        friendlyDate: String
    ): NoticeEntity? {

        val id = dto.id?.trim().takeUnless { it.isNullOrBlank() } ?: return null
        val title = dto.title?.trim().takeUnless { it.isNullOrBlank() } ?: return null
        val body = dto.noticeBody?.trim().takeUnless { it.isNullOrBlank() } ?: return null
        val rawDate = dto.datePublished?.trim().takeUnless { it.isNullOrBlank() } ?: return null

        return NoticeEntity(
            id = id,
            title = title,
            body = body,
            imageUrl = dto.imageUrl?.trim()?.takeIf { it.isNotBlank() },
            publishedAtEpochMillis = epochMillis,
            publishedAtRaw = rawDate,
            publishedAtFriendlyLocal = friendlyDate
        )
    }

    /**
     * Database Entity -> Domain Model
     */
    fun entityToDomain(entity: NoticeEntity): Notice =
        Notice(
            id = entity.id,
            title = entity.title,
            body = entity.body,
            imageUrl = entity.imageUrl,
            publishedAtEpochMillis = entity.publishedAtEpochMillis,
            publishedAtRaw = entity.publishedAtRaw,
            publishedAtFriendlyLocal = entity.publishedAtFriendlyLocal
        )
}