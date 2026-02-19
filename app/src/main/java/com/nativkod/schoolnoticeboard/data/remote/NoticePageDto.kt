package com.nativkod.schoolnoticeboard.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NoticePageDto(
    @param:Json(name = "data") val items: List<NoticeDto?>? = null,
    @param:Json(name = "links") val links: LinksDto? = null,
    @param:Json(name = "pagination") val pagination: PaginationDto? = null
)