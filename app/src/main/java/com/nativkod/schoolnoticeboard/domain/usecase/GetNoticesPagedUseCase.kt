package com.nativkod.schoolnoticeboard.domain.usecase

import androidx.paging.PagingData
import com.nativkod.schoolnoticeboard.domain.model.Notice
import com.nativkod.schoolnoticeboard.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class GetNoticesPagedUseCase @Inject constructor(
    private val repository: NoticeRepository
) {
    open operator fun invoke(): Flow<PagingData<Notice>> {
        return repository.observeNoticesPaged()
    }
}