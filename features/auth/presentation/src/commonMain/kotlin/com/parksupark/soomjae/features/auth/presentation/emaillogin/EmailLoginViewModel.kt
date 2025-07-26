package com.parksupark.soomjae.features.auth.presentation.emaillogin

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.core.presentation.ui.errors.asUiText
import com.parksupark.soomjae.core.presentation.ui.utils.collectAsFlow
import com.parksupark.soomjae.features.auth.domain.AuthRepository
import com.parksupark.soomjae.features.auth.domain.UserDataValidator
import com.parksupark.soomjae.features.auth.presentation.navigation.AuthDestination
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EmailLoginViewModel(
    savedStateHandle: SavedStateHandle,
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository,
) : ViewModel() {
    val email = savedStateHandle.get<String>(AuthDestination.EmailLogin::email.name) ?: ""

    private val _uiStateFlow: MutableStateFlow<EmailLoginState> = MutableStateFlow(
        EmailLoginState(inputEmail = TextFieldState(email)),
    )
    val uiStateFlow: StateFlow<EmailLoginState> = _uiStateFlow.onStart {
        if (email.isBlank()) {
            loadSavedEmail()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = _uiStateFlow.value,
    )

    private val _eventChannel = Channel<EmailLoginEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        uiStateFlow.distinctUntilChangedBy { it.inputEmail }.onEach {
            uiStateFlow.value.inputEmail.collectAsFlow().onEach { email ->
                _uiStateFlow.update {
                    val isEmailValid = userDataValidator.isValidEmail(email.toString())
                    it.copy(isEmailValid = isEmailValid)
                }
            }.launchIn(viewModelScope)
        }.launchIn(viewModelScope)

        uiStateFlow.value.inputPassword.collectAsFlow().onEach { password ->
            _uiStateFlow.update {
                val isPasswordValid = password.toString().isNotBlank()
                it.copy(isPasswordValid = isPasswordValid)
            }
        }.launchIn(viewModelScope)

        uiStateFlow.distinctUntilChanged { old, new ->
            old.isEmailValid == new.isEmailValid &&
                old.isPasswordValid == new.isPasswordValid
        }.map { state ->
            state.isEmailValid && state.isPasswordValid
        }.onEach { canLogin ->
            _uiStateFlow.update {
                it.copy(canLogin = canLogin)
            }
        }.launchIn(viewModelScope)
    }

    fun login() {
        viewModelScope.launch {
            _uiStateFlow.update { it.copy(isLoggingIn = true) }

            authRepository.login(
                email = uiStateFlow.value.inputEmail.text.toString(),
                password = uiStateFlow.value.inputPassword.text.toString(),
            ).fold(
                ifLeft = {
                    _eventChannel.send(
                        EmailLoginEvent.Error(it.asUiText()),
                    )
                },
                ifRight = {
                    if (uiStateFlow.value.shouldSaveEmail) {
                        authRepository.saveEmail(uiStateFlow.value.inputEmail.text.toString())
                    } else {
                        authRepository.clearSavedEmail()
                    }

                    _eventChannel.send(
                        EmailLoginEvent.LoginSuccess,
                    )
                },
            )

            _uiStateFlow.update { it.copy(isLoggingIn = true) }
        }
    }

    fun toggleSaveEmail() {
        _uiStateFlow.update {
            it.copy(shouldSaveEmail = !it.shouldSaveEmail)
        }
    }

    private suspend fun loadSavedEmail() {
        val savedEmail = authRepository.loadSavedEmail()

        savedEmail.fold(
            ifLeft = {
                if (it == DataFailure.Local.NOT_FOUND) {
                    Logger.d(TAG) { "No saved email" }
                } else {
                    Logger.e(TAG) { "Failed to load saved email" }
                }
            },
            ifRight = { email ->
                _uiStateFlow.update {
                    it.copy(inputEmail = TextFieldState(email), shouldSaveEmail = true)
                }
            },
        )
    }
}

private const val TAG = "EmailLoginViewModel"
