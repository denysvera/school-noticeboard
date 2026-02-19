package com.nativkod.schoolnoticeboard.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.nativkod.schoolnoticeboard.presentation.ui.components.EmptyState
import com.nativkod.schoolnoticeboard.presentation.ui.components.ErrorOverlay
import com.nativkod.schoolnoticeboard.presentation.ui.components.NoticeList
import com.nativkod.schoolnoticeboard.presentation.vm.NoticeFeedViewModel

private const val FEED_TAG = "screen_feed"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoticeFeedScreen(onNoticeClick: (String) -> Unit) {
    val vm: NoticeFeedViewModel = hiltViewModel()
    val items = vm.notices.collectAsLazyPagingItems()
    val refresh = items.loadState.refresh

    Scaffold(
        modifier = Modifier.testTag(FEED_TAG),
        topBar = {
            TopAppBar(
                title = { Text("Noticeboard") },
                actions = {
                    if (refresh !is LoadState.Loading) {
                        TextButton(onClick = { items.refresh() }) { Text("Refresh") }
                    }
                }
            )
        }
    ) { padding ->
        when {
            refresh is LoadState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
            }

            refresh is LoadState.Error -> {
                Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                    ErrorOverlay(
                        message = refresh.error.message ?: "Failed to load notices",
                        onRetry = { items.retry() }
                    )
                }
            }

            refresh is LoadState.NotLoading && items.itemCount == 0 -> {
                Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                    EmptyState(onRefresh = { items.refresh() })
                }
            }

            else -> {
                Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                    NoticeList(items = items, onNoticeClick = onNoticeClick)

                    if (refresh is LoadState.Loading) {
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}








