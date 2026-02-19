package com.nativkod.schoolnoticeboard.core.util

interface DateFormatter {
    /** Returns a user-facing string already localized to the device timezone. */
    suspend fun friendlyLocalFromUtcIso(utcIso: String): String

    /** Parses UTC ISO 8601 into epoch millis, or null on failure. */
    fun utcIsoToEpochMillisOrNull(utcIso: String): Long?
}