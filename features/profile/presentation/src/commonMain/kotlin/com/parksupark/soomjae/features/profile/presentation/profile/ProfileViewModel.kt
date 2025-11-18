package com.parksupark.soomjae.features.profile.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.domain.logging.SjLogger
import com.parksupark.soomjae.features.profile.domain.model.Profile
import com.parksupark.soomjae.features.profile.domain.repository.ProfileRepository
import com.parksupark.soomjae.features.profile.presentation.profile.model.UserUi
import com.parksupark.soomjae.features.profile.presentation.profile.model.mapper.toUserUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "ProfileViewModel"

internal class ProfileViewModel(
    private val dispatcher: SoomjaeDispatcher,
    private val logger: SjLogger,
    private val sessionRepository: SessionRepository,
    private val profileRepository: ProfileRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<ProfileState>(
        ProfileState.My(isLoggedIn = false)
    )
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        observeAuthState()
    }

    private fun observeAuthState() {
        sessionRepository.getAsFlow()
            .onEach { auth ->
                if (auth == null) {
                    reduceToLoggedOut()
                } else {
                    reduceToMyProfileBasic(auth.memberId)
                    loadUserProfile(auth.memberId)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun reduceToLoggedOut() {
        _state.update {
            ProfileState.My(
                isLoggedIn = false,
                isLoading = false,
                user = UserUi.Default
            )
        }
    }

    private fun reduceToMyProfileBasic(memberId: Long) {
        _state.update {
            ProfileState.My(
                isLoggedIn = true,
                isLoading = true,
                user = UserUi(
                    id = memberId,
                    nickname = "",
                    profileImageUrl = "",
                )
            )
        }
    }

    private fun reduceWithProfile(profile: Profile, isMyProfile: Boolean) {
        val userUi = profile.toUserUi()

        _state.update {
            if (isMyProfile) {
                ProfileState.My(
                    isLoggedIn = true,
                    isLoading = false,
                    user = userUi,
                )
            } else {
                ProfileState.Other(
                    isLoading = false,
                    user = userUi,
                )
            }
        }
    }

    fun loadUserProfile(memberId: Long) {
        viewModelScope.launch {
            _state.update { it.copyLoading(true) }

            val result = withContext(dispatcher.io) {
                profileRepository.getProfile(memberId)
            }

            result.fold(
                ifLeft = { failure ->
                    logger.error(
                        TAG,
                        "Failed to load profile for memberId: $memberId with $failure",
                    )
                    _state.update { it.copyLoading(false) }
                },
                ifRight = { profile ->
                    val isMy = sessionRepository.get()?.memberId == memberId
                    reduceWithProfile(profile, isMy)
                }
            )
        }
    }

    fun logout() {
        viewModelScope.launch { sessionRepository.set(null) }
    }
}

private fun ProfileState.copyLoading(loading: Boolean): ProfileState =
    when (this) {
        is ProfileState.My -> copy(isLoading = loading)
        is ProfileState.Other -> copy(isLoading = loading)
    }
