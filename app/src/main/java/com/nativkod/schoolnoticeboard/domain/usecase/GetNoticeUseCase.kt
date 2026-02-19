package com.nativkod.schoolnoticeboard.domain.usecase

import com.nativkod.schoolnoticeboard.domain.model.Notice
import com.nativkod.schoolnoticeboard.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoticeUseCase @Inject constructor(
    private val repository: NoticeRepository
) {
    operator fun invoke(id: String): Flow<Notice?> {
        return repository.observeNotice(id)
    }
}