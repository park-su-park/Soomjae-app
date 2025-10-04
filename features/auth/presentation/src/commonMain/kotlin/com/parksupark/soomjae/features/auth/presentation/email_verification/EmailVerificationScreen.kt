@file:OptIn(ExperimentalTime::class)

package com.parksupark.soomjae.features.auth.presentation.email_verification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSecondaryButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTextField
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val SECONDS_PER_MINUTE = 60

@Composable
internal fun EmailVerificationScreen(
    state: EmailVerificationState,
    onAction: (EmailVerificationAction) -> Unit,
) {
    SoomjaeScaffold { innerPadding ->
        EmailVerificationContent(
            state = state,
            onAction = onAction,
            modifier = Modifier.fillMaxSize().padding(innerPadding),
        )
    }
}

@Composable
private fun EmailVerificationContent(
    state: EmailVerificationState,
    onAction: (EmailVerificationAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val emailState = state.email
    val codeState = state.code

    var now by remember { mutableStateOf(Clock.System.now()) }
    val timerEnd = state.timerEnd
    val timerText = now.toTimerEnd(state.timerEnd)

    LaunchedEffect(timerEnd, onAction) {
        if (timerEnd != null && timerEnd > now) {
            while (true) {
                delay(1000)
                onAction(EmailVerificationAction.OnTimerTick(Clock.System.now()))
                now = Clock.System.now()
            }
        }
    }

    Column(
        modifier = modifier.padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        EmailInputField(
            state = emailState,
            isVerifying = state.isVerifying,
            errorMessage = state.errorMessage,
        )
        Spacer(modifier = Modifier.height(16.dp))
        CodeInputWithTimerAndResend(
            codeState = codeState,
            timerText = timerText,
            isVerifying = state.isVerifying,
            errorMessage = state.errorMessage,
            resendStatus = state.resendStatus,
            isResendEnabled = state.isResendEnabled,
            onResend = { onAction(EmailVerificationAction.OnClickResend(Clock.System.now())) },
        )
        Spacer(modifier = Modifier.height(24.dp))
        SoomjaeButton(
            onClick = { onAction(EmailVerificationAction.OnClickVerify) },
            enabled = state.canSubmitVerification,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("인증하기")
        }
        if (state.errorMessage != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(state.errorMessage, color = androidx.compose.ui.graphics.Color.Red)
        }
    }
}

@Composable
private fun EmailInputField(
    state: TextFieldState,
    isVerifying: Boolean,
    errorMessage: String?,
) {
    SoomjaeTextField(
        state = state,
        hint = "이메일을 입력하세요",
        title = "이메일",
        keyboardType = KeyboardType.Email,
        enabled = !isVerifying,
        error = errorMessage?.takeIf { state.text.isEmpty() },
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun CodeInputWithTimerAndResend(
    codeState: TextFieldState,
    timerText: String,
    isVerifying: Boolean,
    errorMessage: String?,
    resendStatus: ResendStatus,
    isResendEnabled: Boolean,
    onResend: () -> Unit,
) {
    val isFirstSend = resendStatus == ResendStatus.Idle
    val shouldShowTimer = resendStatus != ResendStatus.Idle
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
    ) {
        SoomjaeTextField(
            state = codeState,
            hint = "인증번호 6자리를 입력하세요",
            title = "인증번호",
            keyboardType = KeyboardType.Text,
            enabled = !isVerifying,
            error = errorMessage?.takeIf { codeState.text.isEmpty() },
            modifier = Modifier.weight(1f),
            endContent = {
                if (shouldShowTimer) {
                    Text(
                        text = timerText,
                        style = SoomjaeTheme.typography.labelM,
                    )
                }
            },
            additionalInfo = when (resendStatus) {
                ResendStatus.Idle -> null
                ResendStatus.Success -> "인증번호를 발송했어요."
                is ResendStatus.Error -> "인증번호 발송에 실패했어요."
            },
        )
        Spacer(modifier = Modifier.width(8.dp))
        SoomjaeSecondaryButton(
            onClick = onResend,
            enabled = isResendEnabled && !isVerifying,
        ) {
            Text(if (isFirstSend) "전송" else "재전송")
        }
    }
}

private fun Instant.toTimerEnd(end: Instant?): String {
    val remainingSeconds = if (end != null && end > this) {
        (end - this).inWholeSeconds.coerceAtLeast(0)
    } else {
        0
    }
    val minutes = (remainingSeconds / SECONDS_PER_MINUTE).toInt()
    val seconds = (remainingSeconds % SECONDS_PER_MINUTE).toInt()
    val timerText = minutes.toString().padStart(2, '0') + ":" + seconds.toString().padStart(2, '0')

    return timerText
}

@Composable
@Preview(name = "EmailVerification")
private fun EmailVerificationScreenPreview() {
    AppTheme {
        EmailVerificationScreen(
            state = EmailVerificationState(),
            onAction = { },
        )
    }
}
