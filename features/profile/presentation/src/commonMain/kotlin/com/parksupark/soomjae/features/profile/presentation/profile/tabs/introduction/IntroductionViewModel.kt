package com.parksupark.soomjae.features.profile.presentation.profile.tabs.introduction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.domain.logging.SjLogger
import com.parksupark.soomjae.features.profile.domain.usecase.GetIntroductionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.annotation.InjectedParam

class IntroductionViewModel(
    @InjectedParam private val memberId: Long,

    private val logger: SjLogger,
    private val dispatcher: SoomjaeDispatcher,

    private val getIntroductionUseCase: GetIntroductionUseCase,
    private val authRepository: SessionRepository,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<IntroductionState> =
        MutableStateFlow(IntroductionState())
    val stateFlow: StateFlow<IntroductionState> = _stateFlow.asStateFlow()

    init {
        observeAuthState()
        loadPost()
    }

    private fun observeAuthState() {
        authRepository.getAsFlow().onEach { authInfo ->
            val isMe = authInfo?.memberId == memberId
            _stateFlow.update {
                it.copy(isMe = isMe)
            }
        }.launchIn(viewModelScope)
    }

    private fun loadPost() {
        viewModelScope.launch {
            _stateFlow.update { it.copy(isRefreshing = true) }

            val result = withContext(dispatcher.io) {
                getIntroductionUseCase(memberId)
            }

            result.fold(
                ifLeft = { failure ->
                    logger.error(TAG, "Failed to load introduction post with $failure")
                    _stateFlow.update {
                        it.copy(isRefreshing = false)
                    }
                },
                ifRight = { introductionPost ->
                    _stateFlow.update {
                        it.copy(
                            content = introductionPost?.htmlContent,
                            isRefreshing = false,
                        )
                    }
                },
            )
        }
    }

    fun refreshPost() {
        loadPost()
    }

    companion object {
        private const val TAG = "IntroductionViewModel"
    }
}
