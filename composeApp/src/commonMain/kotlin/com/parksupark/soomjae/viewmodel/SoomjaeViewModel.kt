package com.parksupark.soomjae.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.data.util.PlatformUtils
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.domain.repository.ColorThemeRepository
import com.parksupark.soomjae.core.notification.domain.service.DeviceTokenService
import com.parksupark.soomjae.core.notification.domain.service.PushNotificationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class SoomjaeViewModel(
    private val sessionRepository: SessionRepository,
    private val pushNotificationService: PushNotificationService,
    private val deviceTokenService: DeviceTokenService,
    colorThemeRepository: ColorThemeRepository,
) : ViewModel() {
    private var hasLoadedInitialData = false

    private val _uiState = MutableStateFlow(SoomjaeState())
    val uiState = _uiState.onStart {
        if (!hasLoadedInitialData) {
            observeSession()
            hasLoadedInitialData = true
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = SoomjaeState(),
    )

    private var currentDeviceToken: String? = null
    private var previousDeviceToken: String? = null

    private fun observeSession() {
        combine(
            sessionRepository.getAsFlow(),
            pushNotificationService.observeDeviceToken(),
        ) { authInfo, deviceToken ->
            currentDeviceToken = deviceToken
            if (authInfo != null && deviceToken != null && deviceToken != previousDeviceToken) {
                registerDeviceToken(deviceToken, PlatformUtils.getOSName())
                previousDeviceToken = deviceToken
            }
        }
            .launchIn(viewModelScope)
    }

    private fun registerDeviceToken(
        deviceToken: String,
        platform: String,
    ) {
        viewModelScope.launch {
            deviceTokenService.registerToken(deviceToken, platform)
        }
    }

    init {
        colorThemeRepository.getColorThemeStream().onEach { theme ->
            _uiState.update { it.copy(theme = theme) }
        }.launchIn(viewModelScope)
    }
}
