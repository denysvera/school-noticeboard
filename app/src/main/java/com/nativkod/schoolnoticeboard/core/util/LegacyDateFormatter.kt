package com.nativkod.schoolnoticeboard.core.util

import com.nativkod.schoolnoticeboard.legacy.LegacyDateProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class LegacyDateFormatter(
    private val zoneId: ZoneId = ZoneId.systemDefault()
) : DateFormatter {

    // LegacyDateProvider uses a static SimpleDateFormat -> NOT thread-safe.
    private val mutex = Mutex()

    private val legacyInputFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.US)

    override fun utcIsoToEpochMillisOrNull(utcIso: String): Long? =
        runCatching { Instant.parse(utcIso).toEpochMilli() }.getOrNull()

    override suspend fun friendlyLocalFromUtcIso(utcIso: String): String {
        // parse UTC ISO -> convert to local -> feed legacy provider format -> legacy output
        val instant = runCatching { Instant.parse(utcIso) }.getOrElse { return "Date Error" }
        val localDateTime = LocalDateTime.ofInstant(instant, zoneId)
        val legacyCompatible = localDateTime.format(legacyInputFormatter)

        // Ensure single-thread access to legacy static formatter.
        return mutex.withLock {
            withContext(Dispatchers.Default) {
                LegacyDateProvider.getFriendlyDate(legacyCompatible)
            }
        }
    }
}