package com.nativkod.schoolnoticeboard.data.mapper

import com.nativkod.schoolnoticeboard.data.local.entity.NoticeEntity
import com.nativkod.schoolnoticeboard.data.remote.NoticeDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class NoticeMapperTest {

    @Test
    fun `dtoToEntityOrNull returns null when id missing`() {
        val dto = NoticeDto(
            id = null,
            title = "Title",
            imageUrl = null,
            noticeBody = "Body",
            datePublished = "2026-01-16T08:00:00Z"
        )

        val entity = NoticeMapper.dtoToEntityOrNull(
            dto = dto,
            epochMillis = 1000L,
            friendlyDate = "Friendly Date"
        )

        assertNull(entity)
    }

    @Test
    fun `dtoToEntityOrNull returns null when body missing`() {
        val dto = NoticeDto(
            id = "1",
            title = "Title",
            imageUrl = null,
            noticeBody = null,
            datePublished = "2026-01-16T08:00:00Z"
        )

        val entity = NoticeMapper.dtoToEntityOrNull(
            dto = dto,
            epochMillis = 1000L,
            friendlyDate = "Friendly Date"
        )

        assertNull(entity)
    }

    @Test
    fun `dtoToEntityOrNull maps valid dto correctly`() {
        val dto = NoticeDto(
            id = "abc123",
            title = "School Closed",
            imageUrl = "https://example.com/image.jpg",
            noticeBody = "Due to weather conditions",
            datePublished = "2026-01-16T08:00:00Z"
        )

        val entity = NoticeMapper.dtoToEntityOrNull(
            dto = dto,
            epochMillis = 5000L,
            friendlyDate = "Fri Jan 16 10:00:00 SAST 2026"
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

    @Test
    fun `entityToDomain maps entity correctly`() {
        val entity = NoticeEntity(
            id = "1",
            title = "Title",
            body = "Body",
            imageUrl = null,
            publishedAtEpochMillis = 1234L,
            publishedAtRaw = "2026-01-16T08:00:00Z",
            publishedAtFriendlyLocal = "Friendly"
        )

        val domain = NoticeMapper.entityToDomain(entity)

        assertEquals("1", domain.id)
        assertEquals("Title", domain.title)
        assertEquals("Body", domain.body)
        assertEquals(null, domain.imageUrl)
        assertEquals(1234L, domain.publishedAtEpochMillis)
        assertEquals("2026-01-16T08:00:00Z", domain.publishedAtRaw)
        assertEquals("Friendly", domain.publishedAtFriendlyLocal)
    }

    @Test
    fun `dtoToEntityOrNull returns null when title blank`() {
        val dto = NoticeDto(
            id = "1",
            title = "   ",
            imageUrl = null,
            noticeBody = "Body",
            datePublished = "2026-01-16T08:00:00Z"
        )

        val entity = NoticeMapper.dtoToEntityOrNull(
            dto = dto,
            epochMillis = 1000L,
            friendlyDate = "Friendly Date"
        )

        assertNull(entity)
    }
    @Test
    fun `dtoToEntityOrNull trims incoming api strings`() {
        val dto = NoticeDto(
            id = "  abc123  ",
            title = "  School Closed  ",
            imageUrl = "  https://example.com/image.jpg  ",
            noticeBody = "  Due to weather  ",
            datePublished = "2026-01-16T08:00:00Z"
        )

        val entity = NoticeMapper.dtoToEntityOrNull(
            dto = dto,
            epochMillis = 5000L,
            friendlyDate = "Friendly"
        )

        assertNotNull(entity)
        entity!!

        // Notice all values are trimmed
        assertEquals("abc123", entity.id)
        assertEquals("School Closed", entity.title)
        assertEquals("Due to weather", entity.body)
        assertEquals("https://example.com/image.jpg", entity.imageUrl)
    }
}