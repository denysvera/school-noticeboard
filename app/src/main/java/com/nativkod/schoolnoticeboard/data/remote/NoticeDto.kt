package com.nativkod.schoolnoticeboard.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NoticeDto(
    @param:Json(name = "id") val id: String? = null,
    @param:Json(name = "title") val title: String? = null,
    @param:Json(name = "image_url") val imageUrl: String? = null,
    @param:Json(name = "notice_body") val noticeBody: String? = null,
    @param:Json(name = "date_published") val datePublished: String? = null
)