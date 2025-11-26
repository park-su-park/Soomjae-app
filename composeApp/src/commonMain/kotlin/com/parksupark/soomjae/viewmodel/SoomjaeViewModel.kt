package com.parksupark.soomjae.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.core.data.util.PlatformUtils
import com.parksupark.soomjae.core.domain.auth.repositories.SessionRepository
import com.parksupark.soomjae.core.domain.logging.SjLogger
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

private const val TAG = "SoomjaeViewModel"

internal class SoomjaeViewModel(
    private val dispatcher: SoomjaeDispatcher,
    private val logger: SjLogger,
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
            pushNotificationService.observeDeviceId(),
        ) { authInfo, deviceToken, deviceId ->
            currentDeviceToken = deviceToken
            if (authInfo != null && deviceToken != null && deviceToken != previousDeviceToken && deviceId != null) {
                registerDeviceToken(deviceToken, deviceId, PlatformUtils.getOSName())
                previousDeviceToken = deviceToken
            }
        }
            .launchIn(viewModelScope)
    }

    private fun registerDeviceToken(
        deviceToken: String,
        deviceId: String,
        platform: String,
    ) {
        viewModelScope.launch(dispatcher.io) {
            deviceTokenService.registerToken(deviceToken, deviceId, platform).fold(
                ifLeft = {
                    logger.error(TAG, "Failed to register device token: $deviceToken")
                },
                ifRight = {
                    logger.info(TAG, "Successfully registered device token: $deviceToken")
                },
            )
        }
    }

    init {
        colorThemeRepository.getColorThemeStream().onEach { theme ->
            _uiState.update { it.copy(theme = theme) }
        }.launchIn(viewModelScope)
    }
}
