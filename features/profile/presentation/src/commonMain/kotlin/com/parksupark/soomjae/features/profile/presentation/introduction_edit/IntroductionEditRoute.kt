package com.parksupark.soomjae.features.profile.presentation.introduction_edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.features.profile.presentation.navigation.ProfileNavigator

@Composable
internal fun IntroductionEditRoute(
    navigator: ProfileNavigator,
    coordinator: IntroductionEditCoordinator =
        rememberIntroductionEditCoordinator(navigator = navigator),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()
    val event = coordinator.events

    val actionHandler: (IntroductionEditActions) -> Unit = { action ->
        coordinator.handle(action)
    }

    ObserveAsEvents(
        flow = event,
        onEvent = { event ->
            when (event) {
                is IntroductionEditEvents.Error -> { }
                IntroductionEditEvents.SaveIntroductionSuccess -> coordinator.navigateBack()
            }
        },
    )

    IntroductionEditScreen(
        state = uiState,
        onAction = actionHandler,
    )
}
