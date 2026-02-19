package com.nativkod.schoolnoticeboard.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.nativkod.schoolnoticeboard.core.database.AppDatabase
import com.nativkod.schoolnoticeboard.data.remote.api.NoticeboardApi
import com.nativkod.schoolnoticeboard.core.util.DateFormatter
import com.nativkod.schoolnoticeboard.data.mapper.NoticeMapper
import com.nativkod.schoolnoticeboard.data.paging.NoticeRemoteMediator
import com.nativkod.schoolnoticeboard.domain.model.Notice
import com.nativkod.schoolnoticeboard.domain.repository.NoticeRepository
import jakarta.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class NoticeRepositoryImpl @Inject constructor(
    private val api: NoticeboardApi,
    private val db: AppDatabase,
    private val dateFormatter: DateFormatter
) : NoticeRepository {
   private val dao = db.noticeDao()
    companion object {
        const val FIRST_PAGE_URL = "https://cdnrly.d6.co.za/mock_api/android/page_1.json"
    }
    @OptIn(ExperimentalPagingApi::class)
    override fun observeNoticesPaged(): Flow<PagingData<Notice>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = NoticeRemoteMediator(api, db, FIRST_PAGE_URL, dateFormatter),
            pagingSourceFactory = { dao.pagingSource() }
        ).flow.map { it.map { entity -> NoticeMapper.entityToDomain(entity) } }
    }

    override fun observeNotice(id: String): Flow<Notice?> =
        db.noticeDao()
            .observeNotice(id)
            .map { it?.let(NoticeMapper::entityToDomain) }
}
