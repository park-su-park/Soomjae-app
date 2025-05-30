package com.parksupark.soomjae.features.post.presentation.post.tabs.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.parksupark.soomjae.features.post.domain.repositories.CommunityRepository
import com.parksupark.soomjae.features.post.presentation.post.models.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

internal class CommunityTabViewModel(
    repository: CommunityRepository,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<CommunityTabState> = MutableStateFlow(CommunityTabState())
    val stateFlow: StateFlow<CommunityTabState> = _stateFlow.asStateFlow()

    init {
        repository.getAllPosts()
            .cachedIn(viewModelScope)
            .map { it.map { post -> post.toUi() } }
            .onEach { posts -> _stateFlow.update { it.copy(posts = posts) } }
            .launchIn(viewModelScope)
    }
}
