package com.nativkod.schoolnoticeboard.presentation.vm

import androidx.paging.PagingData
import com.nativkod.schoolnoticeboard.domain.model.Notice
import com.nativkod.schoolnoticeboard.domain.repository.NoticeRepository
import com.nativkod.schoolnoticeboard.domain.usecase.GetNoticesPagedUseCase
import com.nativkod.schoolnoticeboard.testing.MainDispatcherRule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test

private class DummyNoticeRepository : NoticeRepository {
    override fun observeNoticesPaged(): Flow<PagingData<Notice>> = flowOf(PagingData.empty())
    override fun observeNotice(id: String) = flowOf(null)
}

private class FakeGetNoticesPagedUseCase(
    private val flow: Flow<PagingData<Notice>> = flowOf(PagingData.empty())
) : GetNoticesPagedUseCase(DummyNoticeRepository()) {

    var invokeCount = 0
        private set

    override operator fun invoke(): Flow<PagingData<Notice>> {
        invokeCount++
        return flow
    }
}

class NoticeFeedViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun notices_is_exposed() {
        val fake = FakeGetNoticesPagedUseCase()
        val vm = NoticeFeedViewModel(fake)
        assertNotNull(vm.notices)
    }

    @Test
    fun useCase_is_invoked_once_on_init() {
        val fake = FakeGetNoticesPagedUseCase()
        NoticeFeedViewModel(fake)
        assertEquals(1, fake.invokeCount)
    }
}