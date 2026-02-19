package com.nativkod.schoolnoticeboard.core.util

import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import java.time.ZoneId

class LegacyDateFormatterTest {

    @Test
    fun `invalid date returns Date Error`() = runTest {
        val formatter = LegacyDateFormatter(ZoneId.of("Africa/Johannesburg"))

        val result = formatter.friendlyLocalFromUtcIso("bad-date")

        assertEquals("Date Error", result)
    }

    @Test
    fun `valid UTC date converts to friendly string`() = runTest {
        val formatter = LegacyDateFormatter(ZoneId.of("Africa/Johannesburg"))

        val result = formatter.friendlyLocalFromUtcIso("2026-01-16T08:00:00Z")

        assertNotNull(result)
        assertNotEquals("Date Error", result)

        // Don't assert exact wording — JVM date.toString() varies.
        assertTrue(result.length > 10)
    }

    @Test
    fun `epoch conversion works`() {
        val formatter = LegacyDateFormatter(ZoneId.of("Africa/Johannesburg"))

        val epoch = formatter.utcIsoToEpochMillisOrNull("2026-01-16T08:00:00Z")

        assertNotNull(epoch)
    }
}