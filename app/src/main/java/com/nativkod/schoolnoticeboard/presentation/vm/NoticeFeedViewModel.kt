package com.nativkod.schoolnoticeboard.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nativkod.schoolnoticeboard.domain.usecase.GetNoticesPagedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoticeFeedViewModel @Inject constructor(
    getNoticesPagedUseCase: GetNoticesPagedUseCase
) : ViewModel() {

    val notices = getNoticesPagedUseCase().cachedIn(viewModelScope)
}