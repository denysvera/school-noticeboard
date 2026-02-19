package com.nativkod.schoolnoticeboard.data.paging

import androidx.paging.*
import androidx.room.withTransaction
import com.nativkod.schoolnoticeboard.core.database.AppDatabase
import com.nativkod.schoolnoticeboard.data.remote.api.NoticeboardApi
import com.nativkod.schoolnoticeboard.core.util.DateFormatter
import com.nativkod.schoolnoticeboard.data.local.entity.NoticeEntity
import com.nativkod.schoolnoticeboard.data.local.entity.RemoteKeyEntity
import com.nativkod.schoolnoticeboard.data.mapper.NoticeMapper

@OptIn(ExperimentalPagingApi::class)
class NoticeRemoteMediator(
    private val api: NoticeboardApi,
    private val db: AppDatabase,
    private val firstPageUrl: String,
    private val dateFormatter: DateFormatter
) : RemoteMediator<Int, NoticeEntity>() {

    private val noticeDao = db.noticeDao()
    private val keyDao = db.remoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        val hasData = db.noticeDao().count() > 0
        return if (hasData) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NoticeEntity>
    ): MediatorResult {

        try {

            val url = when (loadType) {

                LoadType.REFRESH -> firstPageUrl

                LoadType.PREPEND -> {
                    val first = state.firstItemOrNull() ?: return MediatorResult.Success(true)
                    val key = keyDao.remoteKeyById(first.id)
                    key?.prevKey ?: return MediatorResult.Success(true)
                }

                LoadType.APPEND -> {
                    val last = state.lastItemOrNull() ?: return MediatorResult.Success(true)
                    val key = keyDao.remoteKeyById(last.id)
                    key?.nextKey ?: return MediatorResult.Success(true)
                }
            }

            val response = api.getPage(url)

            val entities = response.items.orEmpty()
                .filterNotNull()
                .mapNotNull { dto ->
                    val raw = dto.datePublished?.trim().takeUnless { it.isNullOrBlank() } ?: return@mapNotNull null

                    val epoch = dateFormatter.utcIsoToEpochMillisOrNull(raw) ?: return@mapNotNull null
                    val friendly = dateFormatter.friendlyLocalFromUtcIso(raw) // returns "Date Error" safely

                    NoticeMapper.dtoToEntityOrNull(dto, epoch, friendly)
                }

            db.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    keyDao.clearAll()
                    noticeDao.clearAll()
                }

                val keys = entities.map {
                    RemoteKeyEntity(
                        noticeId = it.id,
                        prevKey = response.links?.prev,
                        nextKey = response.links?.next
                    )
                }

                keyDao.insertAll(keys)
                noticeDao.upsertAll(entities)
            }

            return MediatorResult.Success(
                endOfPaginationReached = response.links?.next == null
            )

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}