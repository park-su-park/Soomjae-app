package com.parksupark.soomjae.features.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import co.touchlab.kermit.Logger
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.presentation.ui.errors.asUiText
import com.parksupark.soomjae.features.auth.domain.model.LoginType
import com.parksupark.soomjae.features.auth.domain.repositories.LastLoginRepository
import com.parksupark.soomjae.features.auth.domain.repositories.SocialAuthRepository
import com.parksupark.soomjae.features.auth.libs.google.models.GoogleUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

internal class LoginViewModel(
    private val lastLoginRepository: LastLoginRepository,
    private val socialAuthRepository: SocialAuthRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    fun handleGoogleLoginResult(result: Either<DataFailure.Credential, GoogleUser>) {
        result.fold(
            ifLeft = { failure ->
                Logger.e(TAG) { "Google Login Failed: $failure" }

                if (failure != DataFailure.Credential.CANCELLED) {
                    eventChannel.trySend(LoginEvent.Error(failure.asUiText()))
                }
            },
            ifRight = { user ->
                viewModelScope.launch(Dispatchers.IO) {
                    socialAuthRepository.signInWithGoogle(user.idToken).fold(
                        ifLeft = { failure ->
                            Logger.e(TAG) { "Google Sign-In Failed: $failure" }
                            eventChannel.send(LoginEvent.Error(failure.asUiText()))
                        },
                        ifRight = {
                            Logger.i(TAG) { "Google Sign-In Succeeded" }
                            lastLoginRepository.saveRecentLogin(LoginType.Social.Google)
                            eventChannel.send(LoginEvent.OnLoginSuccess)
                        },
                    )
                }
            },
        )
    }
}

private const val TAG = "LoginViewModel"
