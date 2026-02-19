package com.nativkod.schoolnoticeboard.domain.usecase

import androidx.paging.PagingData
import com.nativkod.schoolnoticeboard.domain.model.Notice
import com.nativkod.schoolnoticeboard.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Test

private class FakePagedRepo : NoticeRepository {
    override fun observeNoticesPaged(): Flow<PagingData<Notice>> = flowOf(PagingData.empty())
    override fun observeNotice(id: String) = flowOf(null)
}

class GetNoticesPagedUseCaseTest {

    @Test
    fun invoke_returns_flow() = runTest {
        val useCase = GetNoticesPagedUseCase(FakePagedRepo())
        val flow = useCase()
        assertNotNull(flow)
    }
}