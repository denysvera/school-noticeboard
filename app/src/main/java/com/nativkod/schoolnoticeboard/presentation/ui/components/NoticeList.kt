package com.nativkod.schoolnoticeboard.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.nativkod.schoolnoticeboard.domain.model.Notice

@Composable
fun NoticeList(
    items: LazyPagingItems<Notice>,
    onNoticeClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(count = items.itemCount) { index ->
            val notice = items[index] ?: return@items
            NoticeRow(notice = notice, onClick = { onNoticeClick(notice.id) })
        }

        item {
            when (val append = items.loadState.append) {
                is LoadState.Loading -> {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) { CircularProgressIndicator() }
                }
                is LoadState.Error -> {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(onClick = { items.retry() }) { Text("Retry loading more") }
                    }
                }
                else -> Unit
            }
        }
    }
}