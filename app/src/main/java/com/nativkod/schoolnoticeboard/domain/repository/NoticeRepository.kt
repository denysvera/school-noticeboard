package com.nativkod.schoolnoticeboard.domain.repository

import androidx.paging.PagingData
import com.nativkod.schoolnoticeboard.domain.model.Notice
import kotlinx.coroutines.flow.Flow

interface NoticeRepository {
    fun observeNoticesPaged(): Flow<PagingData<Notice>>
    fun observeNotice(id: String): Flow<Notice?>
}