package com.nativkod.schoolnoticeboard.di

import android.content.Context
import com.nativkod.schoolnoticeboard.core.security.EncryptedTokenStore
import com.nativkod.schoolnoticeboard.core.security.TokenStore
import com.nativkod.schoolnoticeboard.core.util.DateFormatter
import com.nativkod.schoolnoticeboard.core.util.LegacyDateFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTokenStore(@ApplicationContext context: Context): TokenStore =
        EncryptedTokenStore(context)

    @Provides
    @Singleton
    fun provideDateFormatter(): DateFormatter =
        LegacyDateFormatter()
}