package com.nativkod.schoolnoticeboard.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nativkod.schoolnoticeboard.data.local.entity.NoticeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoticeDao {

    @Query("SELECT * FROM notices ORDER BY publishedAtEpochMillis DESC")
    fun pagingSource(): PagingSource<Int, NoticeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<NoticeEntity>)

    @Query("DELETE FROM notices")
    suspend fun clearAll()

    @Query("SELECT * FROM notices WHERE id = :id LIMIT 1")
    fun observeNotice(id: String): Flow<NoticeEntity?>

    @Query("SELECT COUNT(*) FROM notices")
    suspend fun count(): Int

    @Query("SELECT id FROM notices ORDER BY publishedAtEpochMillis DESC LIMIT :limit")
    suspend fun getIdsOrderedByDateDesc(limit: Int): List<String>
}
