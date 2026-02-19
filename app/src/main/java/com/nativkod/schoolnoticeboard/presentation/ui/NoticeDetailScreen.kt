package com.nativkod.schoolnoticeboard.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nativkod.schoolnoticeboard.presentation.state.NoticeDetailUiState
import com.nativkod.schoolnoticeboard.presentation.ui.components.ErrorOverlay
import com.nativkod.schoolnoticeboard.presentation.ui.components.NoticeDetailContent
import com.nativkod.schoolnoticeboard.presentation.vm.NoticeDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoticeDetailScreen(
    vm: NoticeDetailViewModel,
    onBack: () -> Unit
) {
    val state by vm.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notice") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->

        when (val s = state) {
            NoticeDetailUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
            }

            is NoticeDetailUiState.Error -> {
                ErrorOverlay(
                    message = s.message,
                    onRetry = onBack // simplest: go back; or implement retry if you want
                )
            }

            is NoticeDetailUiState.Data -> {
                NoticeDetailContent(
                    notice = s.notice,
                    padding = padding
                )
            }
        }
    }
}
