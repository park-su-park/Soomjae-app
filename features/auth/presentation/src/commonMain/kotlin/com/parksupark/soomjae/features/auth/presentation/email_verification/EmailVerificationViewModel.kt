package com.parksupark.soomjae.features.auth.presentation.email_verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.presentation.ui.utils.collectAsFlow
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

private const val CODE_LENGTH = 6
private val VERIFICATION_TIMEOUT = 5.minutes

@OptIn(ExperimentalTime::class, ExperimentalCoroutinesApi::class)
class EmailVerificationViewModel : ViewModel() {
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
        val end = now + VERIFICATION_TIMEOUT
        _stateFlow.update {
            it.copy(
                isResendEnabled = false,
                timerEnd = end,
                resendStatus = ResendStatus.Success,
                errorMessage = null,
            )
        }
        // TODO: 실제 이메일 재전송 로직 호출 및 성공/실패에 따라 resendStatus 업데이트
    }

    fun onClickVerify() {
        _stateFlow.update { it.copy(isVerifying = true, errorMessage = null) }
        // TODO: 실제 인증 로직 호출 후 isVerifying=false로 되돌리고 결과 처리
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
