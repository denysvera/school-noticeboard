package com.nativkod.schoolnoticeboard.presentation.vm

import androidx.lifecycle.SavedStateHandle
import com.nativkod.schoolnoticeboard.domain.model.Notice
import com.nativkod.schoolnoticeboard.domain.repository.NoticeRepository
import com.nativkod.schoolnoticeboard.domain.usecase.GetNoticeUseCase
import com.nativkod.schoolnoticeboard.presentation.state.NoticeDetailUiState
import com.nativkod.schoolnoticeboard.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

private class FakeNoticeRepository(
    private val noticeFlow: Flow<Notice?>
) : NoticeRepository {
    override fun observeNoticesPaged() = throw UnsupportedOperationException()
    override fun observeNotice(id: String): Flow<Notice?> = noticeFlow
}

class NoticeDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun uiState_emits_Data_when_notice_exists() = runTest {
        val notice = Notice(
            id = "1",
            title = "Title",
            body = "Body",
            imageUrl = null,
            publishedAtEpochMillis = 1L,
            publishedAtRaw = "raw",
            publishedAtFriendlyLocal = "friendly"
        )

        val vm = NoticeDetailViewModel(
            getNoticeUseCase = GetNoticeUseCase(FakeNoticeRepository(flowOf(notice))),
            savedStateHandle = SavedStateHandle(mapOf("id" to "1"))
        )

        val state = vm.uiState.first { it !is NoticeDetailUiState.Loading }
        assertTrue(state is NoticeDetailUiState.Data)
    }

    @Test
    fun uiState_emits_Error_when_notice_missing() = runTest {
        val vm = NoticeDetailViewModel(
            getNoticeUseCase = GetNoticeUseCase(FakeNoticeRepository(flowOf(null))),
            savedStateHandle = SavedStateHandle(mapOf("id" to "missing"))
        )

        val state = vm.uiState.first { it !is NoticeDetailUiState.Loading }
        assertTrue(state is NoticeDetailUiState.Error)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun uiState_starts_Loading_then_Data() = runTest {
        val shared = MutableSharedFlow<Notice?>(replay = 1) // <-- key change

        val vm = NoticeDetailViewModel(
            getNoticeUseCase = GetNoticeUseCase(FakeNoticeRepository(shared)),
            savedStateHandle = SavedStateHandle(mapOf("id" to "1"))
        )

        // initial state
        assertTrue(vm.uiState.value is NoticeDetailUiState.Loading)

        // emit before collecting is now safe because replay=1
        shared.emit(
            Notice(
                id = "1",
                title = "Title",
                body = "Body",
                imageUrl = null,
                publishedAtEpochMillis = 1L,
                publishedAtRaw = "raw",
                publishedAtFriendlyLocal = "friendly"
            )
        )

        val state = vm.uiState.first { it is NoticeDetailUiState.Data }
        assertTrue(state is NoticeDetailUiState.Data)
    }
}