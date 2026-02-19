package com.nativkod.schoolnoticeboard.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nativkod.schoolnoticeboard.data.local.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<RemoteKeyEntity>)

    @Query("SELECT * FROM remote_keys WHERE noticeId = :id")
    suspend fun remoteKeyById(id: String): RemoteKeyEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun clearAll()
}