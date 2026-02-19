package com.nativkod.schoolnoticeboard.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nativkod.schoolnoticeboard.data.local.dao.NoticeDao
import com.nativkod.schoolnoticeboard.data.local.dao.RemoteKeyDao
import com.nativkod.schoolnoticeboard.data.local.entity.NoticeEntity
import com.nativkod.schoolnoticeboard.data.local.entity.RemoteKeyEntity

@Database(
    entities = [
        NoticeEntity::class,
        RemoteKeyEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noticeDao(): NoticeDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}
