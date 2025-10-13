package com.parksupark.soomjae.features.profile.presentation.profile.tabs.member_post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.parksupark.soomjae.features.profile.domain.repositores.ProfileMemberPostRepository
import com.parksupark.soomjae.features.profile.presentation.profile.tabs.model.toMemberPostUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class ProfileMemberPostViewModel(
    private val memberId: Long,
    private val postRepository: ProfileMemberPostRepository,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<ProfileMemberPostState> = MutableStateFlow(ProfileMemberPostState())
    val stateFlow: StateFlow<ProfileMemberPostState> = _stateFlow.asStateFlow()

    val posts = postRepository.getPagedPosts(memberId)
        .map { pagingData -> pagingData.map { it.toMemberPostUi() } }
        .cachedIn(viewModelScope)
}
