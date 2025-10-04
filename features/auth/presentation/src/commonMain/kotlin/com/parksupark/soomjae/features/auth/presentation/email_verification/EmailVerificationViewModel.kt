package com.parksupark.soomjae.features.auth.presentation.email_verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.core.presentation.ui.errors.asUiText
import com.parksupark.soomjae.core.presentation.ui.utils.collectAsFlow
import com.parksupark.soomjae.features.auth.domain.failures.VerificationFailure
import com.parksupark.soomjae.features.auth.domain.repositories.EmailRepository
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val CODE_LENGTH = 6
private val VERIFICATION_TIMEOUT = 5.minutes

@OptIn(ExperimentalTime::class, ExperimentalCoroutinesApi::class)
class EmailVerificationViewModel(
    private val dispatcher: SoomjaeDispatcher,
    private val emailRepository: EmailRepository,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<EmailVerificationState> = MutableStateFlow(EmailVerificationState())
    val stateFlow: StateFlow<EmailVerificationState> = _stateFlow.asStateFlow()

    init {
        // 이메일 변경 추적 및 타이머/재전송 초기화
        stateFlow
            .map { it.email }
            .distinctUntilChanged()
            .flatMapLatest { emailState ->
                emailState.collectAsFlow()
            }
            .distinctUntilChanged()
            .onEach {
                resetTimerAndResendState()
            }
            .launchIn(viewModelScope)

        // 인증 코드 입력 변경 추적 및 버튼 활성화 여부 관리 (성능 개선)
        stateFlow
            .distinctUntilChanged { old, new ->
                old.code == new.code && old.isVerifying == new.isVerifying
            }
            .filter { !it.isVerifying }
            .flatMapLatest { it.code.collectAsFlow() }
            .onEach { code ->
                val canSubmit = code.length == CODE_LENGTH
                _stateFlow.update { it.copy(canSubmitVerification = canSubmit, errorMessage = null) }
            }
            .launchIn(viewModelScope)
    }

    fun onClickResend(now: Instant) {
        if (stateFlow.value.isResending) return

        val end = now + VERIFICATION_TIMEOUT

        viewModelScope.launch(dispatcher.io) {
            _stateFlow.update {
                it.copy(
                    isResendEnabled = false,
                    isResending = true,
                    errorMessage = null,
                    resendStatus = ResendStatus.Idle,
                )
            }

            val email = stateFlow.value.email.toString()
            emailRepository.sendVerificationCode(email).fold(
                ifLeft = { failure ->
                    _stateFlow.update {
                        it.copy(
                            resendStatus = ResendStatus.Error(failure.asUiText()),
                            isResendEnabled = true,
                            isResending = false,
                            timerEnd = null,
                            errorMessage = "인증 코드 전송에 실패했습니다. 다시 시도해주세요.",
                        )
                    }
                },
                ifRight = {
                    _stateFlow.update {
                        it.copy(
                            isResendEnabled = false,
                            isResending = false,
                            timerEnd = end,
                            resendStatus = ResendStatus.Success,
                            errorMessage = null,
                        )
                    }
                },
            )
        }
    }

    fun onClickVerify() {
        if (stateFlow.value.isVerifying) return

        viewModelScope.launch {
            _stateFlow.update { it.copy(isVerifying = true, errorMessage = null) }

            val email = stateFlow.value.email.toString()
            val code = stateFlow.value.code.toString()
            emailRepository.verifyCode(email, code).fold(
                ifLeft = { failure ->
                    val message = when (failure) {
                        is VerificationFailure.InvalidCode -> "인증 코드가 올바르지 않습니다. 다시 확인해주세요."
                        is VerificationFailure.DataFailure -> failure.error.asUiText().toString()
                    }
                    _stateFlow.update {
                        it.copy(
                            isVerifying = false,
                            errorMessage = message,
                        )
                    }
                },
                ifRight = {
                    _stateFlow.update {
                        it.copy(
                            isVerifying = false,
                            errorMessage = null,
                        )
                    }
                },
            )
        }
    }

    fun onTimerTick(now: Instant) {
        val end = stateFlow.value.timerEnd ?: return
        if (now >= end) {
            _stateFlow.update { it.copy(isResendEnabled = true, timerEnd = null) }
        }
    }

    fun resetTimerAndResendState() {
        _stateFlow.update {
            it.copy(
                timerEnd = null,
                isResendEnabled = true,
                resendStatus = ResendStatus.Idle,
            )
        }
    }
}
