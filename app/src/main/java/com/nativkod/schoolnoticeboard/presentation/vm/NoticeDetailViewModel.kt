package com.nativkod.schoolnoticeboard.presentation.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nativkod.schoolnoticeboard.domain.usecase.GetNoticeUseCase
import com.nativkod.schoolnoticeboard.presentation.state.NoticeDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NoticeDetailViewModel @Inject constructor(
    getNoticeUseCase: GetNoticeUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id: String = requireNotNull(savedStateHandle["id"]) {
        "Notice id missing from navigation arguments"
    }

    val uiState: StateFlow<NoticeDetailUiState> =
        getNoticeUseCase(id)
            .map { notice ->
                if (notice == null) NoticeDetailUiState.Error("Notice not found")
                else NoticeDetailUiState.Data(notice)
            }
            .catch { e ->
                emit(NoticeDetailUiState.Error(e.message ?: "Failed to load notice"))
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                NoticeDetailUiState.Loading
            )
}