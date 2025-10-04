package com.parksupark.soomjae.features.auth.presentation.email_verification

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.auth.presentation.navigation.AuthNavigator
import kotlin.time.ExperimentalTime
import org.koin.compose.viewmodel.koinViewModel

class EmailVerificationCoordinator(
    private val navigator: AuthNavigator,
    private val viewModel: EmailVerificationViewModel,
) {
    val screenStateFlow = viewModel.stateFlow

    @OptIn(ExperimentalTime::class)
    fun handle(action: EmailVerificationAction) {
        when (action) {
            EmailVerificationAction.OnClickBack -> navigator.navigateBack()
            is EmailVerificationAction.OnClickResend -> viewModel.onClickResend(action.now)
            EmailVerificationAction.OnClickVerify -> viewModel.onClickVerify()
            is EmailVerificationAction.OnTimerTick -> viewModel.onTimerTick(action.now)
        }
    }
}

@Composable
fun rememberEmailVerifyCoordinator(
    navigator: AuthNavigator,
    viewModel: EmailVerificationViewModel = koinViewModel(),
): EmailVerificationCoordinator = remember(viewModel) {
    EmailVerificationCoordinator(
        navigator = navigator,
        viewModel = viewModel,
    )
}
