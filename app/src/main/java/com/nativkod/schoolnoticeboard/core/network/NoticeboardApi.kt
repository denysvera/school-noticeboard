package com.nativkod.schoolnoticeboard.core.network

import com.nativkod.schoolnoticeboard.data.remote.NoticePageDto
import retrofit2.http.GET
import retrofit2.http.Url

interface NoticeboardApi {
    @GET
    suspend fun getPage(@Url url: String): NoticePageDto
}
