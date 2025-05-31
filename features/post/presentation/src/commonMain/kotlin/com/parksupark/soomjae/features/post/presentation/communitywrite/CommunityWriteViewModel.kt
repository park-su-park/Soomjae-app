package com.parksupark.soomjae.features.post.presentation.communitywrite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.presentation.ui.utils.collectAsFlow
import com.parksupark.soomjae.features.post.presentation.utils.collectAsHtmlFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class CommunityWriteViewModel : ViewModel() {
    private val _uiStateFlow: MutableStateFlow<CommunityWriteState> = MutableStateFlow(CommunityWriteState())
    val uiStateFlow: StateFlow<CommunityWriteState> = _uiStateFlow.asStateFlow()

    init {
        uiStateFlow.value.inputTitle.collectAsFlow().onEach { title ->
            _uiStateFlow.update {
                val isTitleValid = title.toString().isNotBlank()
                it.copy(isTitleValid = isTitleValid)
            }
        }.launchIn(viewModelScope)

        uiStateFlow.value.inputContent.collectAsHtmlFlow().onEach { content ->
            _uiStateFlow.update {
                val isContentValid = content.isNotBlank()
                it.copy(isContentValid = isContentValid)
            }
        }.launchIn(viewModelScope)

        uiStateFlow.distinctUntilChanged { old, new ->
            old.isTitleValid == new.isTitleValid &&
                old.isContentValid == new.isContentValid &&
                old.isSubmitting == new.isSubmitting
        }.map {
            it.isTitleValid && it.isContentValid && !it.isSubmitting
        }.onEach { canSubmit ->
            _uiStateFlow.update { it.copy(canSubmit = canSubmit) }
        }.launchIn(viewModelScope)
    }
}
