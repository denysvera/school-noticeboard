package com.nativkod.schoolnoticeboard.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nativkod.schoolnoticeboard.core.database.AppDatabase
import com.nativkod.schoolnoticeboard.data.local.entity.NoticeEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoticeDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: NoticeDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        dao = db.noticeDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun getIdsOrderedByDateDesc_returns_most_recent_first() = runTest {
        val old = NoticeEntity(
            id = "1",
            title = "Old",
            body = "Body",
            imageUrl = null,
            publishedAtEpochMillis = 1000L,
            publishedAtRaw = "raw",
            publishedAtFriendlyLocal = "friendly"
        )

        val newer = NoticeEntity(
            id = "2",
            title = "New",
            body = "Body",
            imageUrl = null,
            publishedAtEpochMillis = 2000L,
            publishedAtRaw = "raw",
            publishedAtFriendlyLocal = "friendly"
        )

        val newest = NoticeEntity(
            id = "3",
            title = "Newest",
            body = "Body",
            imageUrl = null,
            publishedAtEpochMillis = 3000L,
            publishedAtRaw = "raw",
            publishedAtFriendlyLocal = "friendly"
        )

        dao.upsertAll(listOf(old, newest, newer))

        val ids = dao.getIdsOrderedByDateDesc(limit = 10)

        Assert.assertEquals(listOf("3", "2", "1"), ids)
    }
}