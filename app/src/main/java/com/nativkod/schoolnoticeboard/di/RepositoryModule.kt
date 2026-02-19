package com.nativkod.schoolnoticeboard.di

import com.nativkod.schoolnoticeboard.data.repository.NoticeRepositoryImpl
import com.nativkod.schoolnoticeboard.domain.repository.NoticeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNoticeRepository(
        impl: NoticeRepositoryImpl
    ): NoticeRepository
}