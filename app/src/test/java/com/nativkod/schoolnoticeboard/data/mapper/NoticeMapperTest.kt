package com.nativkod.schoolnoticeboard.data.mapper

import com.nativkod.schoolnoticeboard.data.remote.NoticeDto
import org.junit.Assert.*
import org.junit.Test

class NoticeMapperTest {

    @Test
    fun `mapper returns null when id missing`() {
        val dto = NoticeDto(
            id = null,
            title = "Title",
            imageUrl = null,
            noticeBody = "Body",
            datePublished = "2026-01-16T08:00:00Z"
        )

        val entity = NoticeMapper.dtoToEntityOrNull(
            dto = dto,
            publishedAtEpochMillis = 1000L,
            friendlyLocal = "Friendly Date"
        )

        assertNull(entity)
    }

    @Test
    fun `mapper returns null when body missing`() {
        val dto = NoticeDto(
            id = "1",
            title = "Title",
            imageUrl = null,
            noticeBody = null,
            datePublished = "2026-01-16T08:00:00Z"
        )

        val entity = NoticeMapper.dtoToEntityOrNull(
            dto = dto,
            publishedAtEpochMillis = 1000L,
            friendlyLocal = "Friendly Date"
        )

        assertNull(entity)
    }

    @Test
    fun `mapper maps valid dto correctly`() {
        val dto = NoticeDto(
            id = "abc123",
            title = "School Closed",
            imageUrl = "https://example.com/image.jpg",
            noticeBody = "Due to weather conditions",
            datePublished = "2026-01-16T08:00:00Z"
        )

        val entity = NoticeMapper.dtoToEntityOrNull(
            dto = dto,
            publishedAtEpochMillis = 5000L,
            friendlyLocal = "Fri Jan 16 10:00:00 SAST 2026"
        )

        assertNotNull(entity)

        entity!!

        assertEquals("abc123", entity.id)
        assertEquals("School Closed", entity.title)
        assertEquals("Due to weather conditions", entity.body)
        assertEquals("https://example.com/image.jpg", entity.imageUrl)
        assertEquals(5000L, entity.publishedAtEpochMillis)
        assertEquals("2026-01-16T08:00:00Z", entity.publishedAtRaw)
        assertEquals("Fri Jan 16 10:00:00 SAST 2026", entity.publishedAtFriendlyLocal)
    }
}