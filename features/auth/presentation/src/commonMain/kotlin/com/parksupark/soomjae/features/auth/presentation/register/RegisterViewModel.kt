package com.parksupark.soomjae.features.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.presentation.ui.errors.asUiText
import com.parksupark.soomjae.core.presentation.ui.utils.collectAsFlow
import com.parksupark.soomjae.features.auth.domain.AuthRepository
import com.parksupark.soomjae.features.auth.domain.UserDataValidator
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class RegisterViewModel(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> = _uiState.asStateFlow()

    private val _eventChannel = Channel<RegisterEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        val inputPasswordFlow = uiState.value.inputPassword.collectAsFlow()
        inputPasswordFlow.onEach { password ->
            val passwordValidationState = userDataValidator.validatePassword(password.toString())
            _uiState.update {
                it.copy(passwordValidationState = passwordValidationState)
            }
        }.launchIn(viewModelScope)

        combine(
            inputPasswordFlow,
            uiState.value.inputConfirmPassword.collectAsFlow(),
        ) { (password, confirmPassword) ->
            val isPasswordMatch = userDataValidator.isPasswordMatch(
                password = password.toString(),
                confirmPassword = confirmPassword.toString(),
            )
            _uiState.update {
                it.copy(isPasswordMatch = isPasswordMatch)
            }
        }.launchIn(viewModelScope)

        uiState.value.inputNickname.collectAsFlow().onEach { nickname ->
            val isNicknameValid = userDataValidator.isValidNickname(nickname.toString())
            _uiState.update {
                it.copy(isNicknameValid = isNicknameValid)
            }
        }.launchIn(viewModelScope)

        uiState.distinctUntilChanged { old, new ->
            old.isEmailFormatValid == new.isEmailFormatValid &&
                old.isEmailAvailable == new.isEmailAvailable &&
                old.passwordValidationState == new.passwordValidationState &&
                old.isPasswordMatch == new.isPasswordMatch &&
                old.isRegistering == new.isRegistering
        }.map { state ->
            state.isEmailFormatValid &&
                state.isEmailAvailable &&
                state.passwordValidationState.isValidPassword &&
                state.isPasswordMatch &&
                !state.isRegistering
        }.onEach { canRegister ->
            _uiState.update {
                it.copy(canRegister = canRegister)
            }
        }.launchIn(viewModelScope)
    }

    fun register() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRegistering = true) }
            val result = authRepository.register(
                email = uiState.value.inputEmail.text.toString().trim(),
                password = uiState.value.inputPassword.text.toString(),
            )
            _uiState.update { it.copy(isRegistering = false) }

            result.fold(
                ifLeft = {
                    _eventChannel.send(RegisterEvent.Error(it.asUiText()))
                },
                ifRight = {
                    _eventChannel.send(RegisterEvent.RegistrationSuccess)
                },
            )
        }
    }

    fun validateEmail() {
        viewModelScope.launch {
            _uiState.update { it.copy(isEmailValidating = true) }

            val email = uiState.value.inputEmail.text.toString().trim()

            val isEmailFormatValid = userDataValidator.isValidEmail(email)
            if (!isEmailFormatValid) {
                _uiState.update {
                    it.copy(isEmailFormatValid = false, isEmailAvailable = false, isEmailValidating = false)
                }
                return@launch
            }

            val isEmailAvailable = authRepository.checkEmailAvailable(email)
            _uiState.update {
                it.copy(isEmailFormatValid = true, isEmailAvailable = isEmailAvailable.isRight(), isEmailValidating = false)
            }
        }
    }
}
