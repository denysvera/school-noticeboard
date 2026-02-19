package com.nativkod.schoolnoticeboard.core.database

import android.content.Context
import androidx.room.Room

object DatabaseFactory {

    fun create(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "school_noticeboard.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
