package com.nativkod.schoolnoticeboard.presentation.state

import com.nativkod.schoolnoticeboard.domain.model.Notice

sealed interface NoticeDetailUiState {
    data object Loading : NoticeDetailUiState
    data class Data(val notice: Notice) : NoticeDetailUiState
    data class Error(val message: String) : NoticeDetailUiState
}