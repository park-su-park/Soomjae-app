@file:OptIn(ExperimentalTime::class)

package com.parksupark.soomjae.features.auth.presentation.email_verification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCenterAlignedTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCircularProgressIndicator
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSecondaryButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTextField
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.components.SoomjaeSnackbarHost
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.auth.presentation.resources.Res
import com.parksupark.soomjae.features.auth.presentation.resources.email_verification_available
import com.parksupark.soomjae.features.auth.presentation.resources.email_verification_back
import com.parksupark.soomjae.features.auth.presentation.resources.email_verification_check
import com.parksupark.soomjae.features.auth.presentation.resources.email_verification_checking
import com.parksupark.soomjae.features.auth.presentation.resources.email_verification_code_hint
import com.parksupark.soomjae.features.auth.presentation.resources.email_verification_code_send_failed
import com.parksupark.soomjae.features.auth.presentation.resources.email_verification_code_sent
import com.parksupark.soomjae.features.auth.presentation.resources.email_verification_code_title
import com.parksupark.soomjae.features.auth.presentation.resources.email_verification_email_hint
import com.parksupark.soomjae.features.auth.presentation.resources.email_verification_email_title
import com.parksupark.soomjae.features.auth.presentation.resources.email_verification_resend
import com.parksupark.soomjae.features.auth.presentation.resources.email_verification_send
import com.parksupark.soomjae.features.auth.presentation.resources.email_verification_submit_button
import com.parksupark.soomjae.features.auth.presentation.resources.email_verification_title
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val SECONDS_PER_MINUTE = 60

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun EmailVerificationScreen(
    state: EmailVerificationState,
    onAction: (EmailVerificationAction) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    if (state.isVerifying) {
        BackHandler(enabled = true) { }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SoomjaeScaffold(
            topBar = {
                EmailVerificationTopBar(
                    onBackClick = { if (!state.isVerifying) onAction(EmailVerificationAction.OnClickBack) },
                    isVerifying = state.isVerifying,
                )
            },
            bottomBar = {
                SubmitButton(
                    canSubmit = state.canSubmitVerification,
                    onSubmitClick = { onAction(EmailVerificationAction.OnClickVerify) },
                )
            },
            snackbarHost = {
                SoomjaeSnackbarHost(hostState = snackbarHostState)
            },
        ) { innerPadding ->
            EmailVerificationContent(
                state = state,
                onAction = onAction,
                modifier = Modifier.fillMaxSize().padding(innerPadding),
            )
        }
        if (state.isVerifying) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable(
                        enabled = true,
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                    ) { /* consume all touch events */ },
                contentAlignment = Alignment.Center,
            ) {
                SoomjaeCircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun SubmitButton(
    canSubmit: Boolean,
    onSubmitClick: () -> Unit,
) {
    SoomjaeButton(
        onClick = onSubmitClick,
        enabled = canSubmit,
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .imePadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        Text(Res.string.email_verification_submit_button.value)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EmailVerificationTopBar(
    onBackClick: () -> Unit,
    isVerifying: Boolean
) {
    SoomjaeCenterAlignedTopAppBar(
        title = {
            Text(text = Res.string.email_verification_title.value)
        },
        navigationIcon = {
            IconButton(onClick = onBackClick, enabled = !isVerifying) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = Res.string.email_verification_back.value,
                )
            }
        },
    )
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
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        EmailInputField(
            state = emailState,
            isVerifying = state.isVerifying,
            errorMessage = state.emailErrorMessage,
            isEmailFormatValid = state.isEmailFormatValid,
            isEmailValidating = state.isEmailValidating,
            isEmailAvailable = state.isEmailAvailable,
            onValidateEmail = { onAction(EmailVerificationAction.OnClickValidateEmail) },
        )
        Spacer(modifier = Modifier.height(16.dp))
        CodeInputWithTimerAndResend(
            codeState = codeState,
            timerText = timerText,
            isVerifying = state.isVerifying,
            errorMessage = state.codeErrorMessage,
            resendStatus = state.resendStatus,
            isResendEnabled = state.isResendEnabled,
            onResend = { onAction(EmailVerificationAction.OnClickResend(Clock.System.now())) },
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun EmailInputField(
    state: TextFieldState,
    isVerifying: Boolean,
    errorMessage: String?,
    isEmailFormatValid: Boolean,
    isEmailValidating: Boolean,
    isEmailAvailable: Boolean,
    onValidateEmail: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
    ) {
        SoomjaeTextField(
            state = state,
            hint = Res.string.email_verification_email_hint.value,
            title = Res.string.email_verification_email_title.value,
            keyboardType = KeyboardType.Email,
            enabled = !isVerifying && !isEmailValidating,
            error = errorMessage?.takeIf { errorMessage.isNotEmpty() },
            modifier = Modifier.weight(1f),
            endIcon = if (isEmailAvailable) Icons.Default.Check else null,
        )
        Spacer(modifier = Modifier.width(8.dp))
        SoomjaeSecondaryButton(
            onClick = onValidateEmail,
            enabled = isEmailFormatValid && !isEmailValidating && !isEmailAvailable && !isVerifying,
        ) {
            when {
                isEmailValidating -> Text(Res.string.email_verification_checking.value)
                isEmailAvailable -> Text(Res.string.email_verification_available.value)
                else -> Text(Res.string.email_verification_check.value)
            }
        }
    }
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
            hint = Res.string.email_verification_code_hint.value,
            title = Res.string.email_verification_code_title.value,
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
                ResendStatus.Success -> Res.string.email_verification_code_sent.value
                is ResendStatus.Error -> Res.string.email_verification_code_send_failed.value
            },
        )
        Spacer(modifier = Modifier.width(8.dp))
        SoomjaeSecondaryButton(
            onClick = onResend,
            enabled = isResendEnabled && !isVerifying,
        ) {
            Text(
                if (isFirstSend) {
                    Res.string.email_verification_send.value
                } else {
                    Res.string.email_verification_resend.value
                },
            )
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
            snackbarHostState = remember { SnackbarHostState() },
        )
    }
}

@Composable
@Preview(name = "EmailVerification")
private fun EmailVerificationScreenPreview_Loading() {
    AppTheme {
        EmailVerificationScreen(
            state = EmailVerificationState(isVerifying = true),
            onAction = { },
            snackbarHostState = remember { SnackbarHostState() },
        )
    }
}
