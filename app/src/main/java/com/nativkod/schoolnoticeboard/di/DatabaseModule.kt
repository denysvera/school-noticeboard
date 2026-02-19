package com.nativkod.schoolnoticeboard.di

import android.content.Context
import androidx.room.Room
import com.nativkod.schoolnoticeboard.core.database.AppDatabase
import com.nativkod.schoolnoticeboard.data.local.dao.NoticeDao
import com.nativkod.schoolnoticeboard.data.local.dao.RemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "school_noticeboard.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideNoticeDao(db: AppDatabase): NoticeDao = db.noticeDao()
    @Provides fun provideRemoteKeyDao(db: AppDatabase): RemoteKeyDao = db.remoteKeyDao()
}