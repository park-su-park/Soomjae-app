package com.parksupark.soomjae.features.setting.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.common.theme.ColorTheme
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.domain.logging.SjLogger
import com.parksupark.soomjae.core.domain.repository.ColorThemeRepository
import com.parksupark.soomjae.core.notification.domain.service.DeviceTokenService
import com.parksupark.soomjae.core.notification.domain.service.PushNotificationService
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "SettingViewModel"

internal class SettingViewModel(
    private val logger: SjLogger,
    private val colorThemeRepository: ColorThemeRepository,
    private val sessionRepository: SessionRepository,
    private val pushNotificationService: PushNotificationService,
    private val deviceTokenService: DeviceTokenService,
) : ViewModel() {
    private val eventChannel = Channel<SettingEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _stateFlow: MutableStateFlow<SettingState> = MutableStateFlow(SettingState())
    val stateFlow: StateFlow<SettingState> = _stateFlow.onStart {
        observeColorTheme()
        observeSession()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SettingState(),
    )

    private fun observeColorTheme() {
        colorThemeRepository.getColorThemeStream()
            .onEach { theme ->
                _stateFlow.update { it.copy(colorTheme = theme) }
            }
            .launchIn(viewModelScope)
    }

    private fun observeSession() {
        sessionRepository.getAsFlow()
            .onEach { session ->
                _stateFlow.update { it.copy(isUserLoggedIn = session != null) }
            }
            .launchIn(viewModelScope)
    }

    fun changeColorTheme(theme: ColorTheme) {
        if (_stateFlow.value.colorTheme == theme) return

        viewModelScope.launch {
            colorThemeRepository.setColorTheme(theme)
        }
    }

    fun logout() {
        viewModelScope.launch {
            val currentToken = pushNotificationService.observeDeviceToken().first()
            currentToken?.let { token ->
                deviceTokenService.unregisterToken(token).fold(
                    ifLeft = {
                        logger.error(TAG, "Failed to unregister device token")
                    },
                    ifRight = {
                        logger.info(TAG, "Successfully unregistered device token")
                    },
                )
            }
            sessionRepository.set(null)
            eventChannel.send(SettingEvent.OnLogoutSuccess)
        }
    }
}
