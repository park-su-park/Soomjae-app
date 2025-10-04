package com.parksupark.soomjae.features.auth.presentation.email_verification

import androidx.compose.foundation.text.input.TextFieldState
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class EmailVerificationState @OptIn(ExperimentalTime::class) constructor(
    val email: TextFieldState = TextFieldState(),
    val code: TextFieldState = TextFieldState(),
    val isResendEnabled: Boolean = false,
    val timerEnd: Instant? = null, // 타이머 종료 시각 (Instant)
    val isVerifying: Boolean = false,
    val resendStatus: ResendStatus = ResendStatus.Idle,
    val errorMessage: String? = null,
    val canSubmitVerification: Boolean = false, // 인증 버튼 활성화 여부
)

@OptIn(ExperimentalTime::class)
sealed interface EmailVerificationAction {
    data object OnClickBack : EmailVerificationAction

    data class OnClickResend(val now: Instant) : EmailVerificationAction

    data object OnClickVerify : EmailVerificationAction

    data class OnTimerTick(val now: Instant) : EmailVerificationAction
}

sealed interface ResendStatus {
    data object Idle : ResendStatus

    data object Success : ResendStatus

    data class Error(val message: String) : ResendStatus
}
