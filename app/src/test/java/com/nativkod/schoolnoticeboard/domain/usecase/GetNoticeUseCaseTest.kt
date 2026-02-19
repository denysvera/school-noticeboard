package com.nativkod.schoolnoticeboard.domain.usecase

import com.nativkod.schoolnoticeboard.domain.model.Notice
import com.nativkod.schoolnoticeboard.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

private class FakeNoticeRepository(
    private val notice: Notice?
) : NoticeRepository {
    override fun observeNoticesPaged() = throw UnsupportedOperationException()
    override fun observeNotice(id: String) = flowOf(notice)
}

class GetNoticeUseCaseTest {

    @Test
    fun getNotice_returns_flow_from_repository() = runTest {
        val expected = Notice(
            id = "1",
            title = "T",
            body = "B",
            imageUrl = null,
            publishedAtEpochMillis = 1L,
            publishedAtRaw = "raw",
            publishedAtFriendlyLocal = "friendly"
        )

        val useCase = GetNoticeUseCase(FakeNoticeRepository(expected))

        val actual = useCase("1") // Flow<Notice?>
        // simplest: collect first emission
        var emitted: Notice? = null
        actual.collect { emitted = it; return@collect }

        assertEquals(expected, emitted)
    }
}