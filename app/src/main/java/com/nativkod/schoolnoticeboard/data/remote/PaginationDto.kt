package com.nativkod.schoolnoticeboard.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaginationDto(
    @param:Json(name = "current_page")val currentPage: Int? = null,
    @param:Json(name = "per_page")val perPage: Int? = null,
    @param:Json(name = "total_items")val totalItems: Int? = null,
    @param:Json(name = "total_pages")val totalPages: Int? = null
)