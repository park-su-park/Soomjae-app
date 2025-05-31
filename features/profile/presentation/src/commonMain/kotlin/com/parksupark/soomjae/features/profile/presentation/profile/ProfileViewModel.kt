package com.parksupark.soomjae.features.profile.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.features.profile.presentation.profile.mdoels.UserUi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class ProfileViewModel(
    private val authRepository: SessionRepository,
) : ViewModel() {
    private val _uiStateFlow: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState.MyProfileState())
    val uiStateFlow: StateFlow<ProfileState> = _uiStateFlow.asStateFlow()

    private var uiJob: Job? = null

    init {
        _uiStateFlow.distinctUntilChangedBy { it::class }.onEach {
            uiJob?.cancel()

            when (it) {
                is ProfileState.MyProfileState -> uiJob = authRepository.getAsFlow().onEach { authInfo ->
                    val isLoggedIn = authInfo != null

                    if (isLoggedIn) {
                        _uiStateFlow.update {
                            ProfileState.MyProfileState(
                                isLoggedIn = true,
                                user = UserUi.Default,
                            )
                        }
                    }
                }.launchIn(viewModelScope)

                is ProfileState.OtherProfileState -> {
                    // Handle OtherProfileState
                }
            }
        }.launchIn(viewModelScope)
    }
}
