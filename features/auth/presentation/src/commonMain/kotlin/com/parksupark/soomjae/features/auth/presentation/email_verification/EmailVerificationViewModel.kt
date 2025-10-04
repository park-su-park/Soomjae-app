package com.parksupark.soomjae.features.auth.presentation.email_verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.core.presentation.ui.errors.asUiText
import com.parksupark.soomjae.core.presentation.ui.utils.collectAsFlow
import com.parksupark.soomjae.features.auth.domain.UserDataValidator
import com.parksupark.soomjae.features.auth.domain.repositories.AuthRepository
import com.parksupark.soomjae.features.auth.domain.repositories.EmailRepository
import com.parksupark.soomjae.features.auth.presentation.failure.asUiText
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val CODE_LENGTH = 6
private val VERIFICATION_TIMEOUT = 5.minutes

// TODO: extract string resources
@OptIn(ExperimentalTime::class, ExperimentalCoroutinesApi::class)
class EmailVerificationViewModel(
    private val dispatcher: SoomjaeDispatcher,
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository,
    private val emailRepository: EmailRepository,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<EmailVerificationState> = MutableStateFlow(EmailVerificationState())
    val stateFlow: StateFlow<EmailVerificationState> = _stateFlow.asStateFlow()

    private val eventChannel = Channel<EmailVerificationEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        // 이메일 변경 추적 및 타이머/재전송 초기화
        stateFlow
            .map { it.email }
            .distinctUntilChanged()
            .flatMapLatest { emailState ->
                emailState.collectAsFlow()
            }
            .distinctUntilChanged()
            .onEach { email ->
                val isEmailFormatValid = userDataValidator.isValidEmail(email.toString())
                _stateFlow.update {
                    it.copy(
                        isEmailFormatValid = isEmailFormatValid,
                        isEmailAvailable = false,
                        isEmailValidating = false,
                        timerEnd = null,
                        resendStatus = ResendStatus.Idle,
                        emailErrorMessage = null, // 이메일 입력 변경 시 에러 초기화
                    )
                }
            }
            .launchIn(viewModelScope)

        // 이메일 사용 가능 여부 변경 추적 및 재전송 버튼 활성화 관리
        stateFlow.distinctUntilChangedBy { it.isEmailAvailable }
            .onEach {
                _stateFlow.update {
                    it.copy(
                        isResendEnabled = it.isEmailAvailable,
                        timerEnd = null,
                        resendStatus = ResendStatus.Idle,
                    )
                }
            }.launchIn(viewModelScope)

        // 인증 코드 입력 변경 추적 및 버튼 활성화 여부 관리 (성능 개선)
        stateFlow
            .distinctUntilChanged { old, new ->
                old.code == new.code && old.isVerifying == new.isVerifying && old.resendStatus == new.resendStatus
            }
            .filter { !it.isVerifying && it.resendStatus == ResendStatus.Success }
            .flatMapLatest { it.code.collectAsFlow() }
            .onEach { code ->
                val canSubmit = code.length == CODE_LENGTH
                _stateFlow.update { it.copy(canSubmitVerification = canSubmit, codeErrorMessage = null) }
            }
            .launchIn(viewModelScope)
    }

    fun validateEmail() {
        viewModelScope.launch(dispatcher.io) {
            _stateFlow.update { state ->
                state.copy(isEmailValidating = true, isEmailAvailable = false, emailErrorMessage = null)
            }

            val email = stateFlow.value.email.toString()
            authRepository.checkEmailAvailable(email).fold(
                ifLeft = { failure ->
                    _stateFlow.update {
                        it.copy(
                            isEmailValidating = false,
                            isEmailAvailable = false,
                            emailErrorMessage = failure.asUiText().toString(),
                        )
                    }
                },
                ifRight = { isAvailable ->
                    _stateFlow.update {
                        it.copy(
                            isEmailValidating = false,
                            isEmailAvailable = isAvailable,
                            emailErrorMessage = if (isAvailable) null else "이미 사용 중인 이메일입니다.",
                        )
                    }
                },
            )
        }
    }

    fun resendVerificationCode(now: Instant) {
        if (!stateFlow.value.isEmailAvailable) return
        if (stateFlow.value.isResending) return

        val end = now + VERIFICATION_TIMEOUT

        viewModelScope.launch(dispatcher.io) {
            _stateFlow.update {
                it.copy(
                    isResendEnabled = false,
                    isResending = true,
                    emailErrorMessage = null,
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
                            emailErrorMessage = "인증 코드 전송에 실패했습니다. 다시 시도해주세요.",
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
                            emailErrorMessage = null,
                        )
                    }
                },
            )
        }
    }

    fun verifyCode() {
        if (!stateFlow.value.canSubmitVerification) return
        if (stateFlow.value.isVerifying) return

        viewModelScope.launch {
            _stateFlow.update { it.copy(isVerifying = true, codeErrorMessage = null) }

            val email = stateFlow.value.email.toString()
            val code = stateFlow.value.code.toString()
            emailRepository.verifyCode(email, code).fold(
                ifLeft = { failure ->
                    _stateFlow.update {
                        it.copy(
                            isVerifying = false,
                        )
                    }
                    eventChannel.send(EmailVerificationEvent.Error(failure.asUiText()))
                },
                ifRight = {
                    _stateFlow.update {
                        it.copy(
                            isVerifying = false,
                        )
                    }
                    eventChannel.send(EmailVerificationEvent.VerificationSuccess)
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
}
