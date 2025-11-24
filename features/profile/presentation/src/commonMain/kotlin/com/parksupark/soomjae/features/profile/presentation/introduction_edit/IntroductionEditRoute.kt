package com.parksupark.soomjae.features.profile.presentation.introduction_edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parksupark.soomjae.features.profile.presentation.navigation.ProfileNavigator

@Composable
internal fun IntroductionEditRoute(
    navigator: ProfileNavigator,
    coordinator: IntroductionEditCoordinator =
        rememberIntroductionEditCoordinator(navigator = navigator),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    val actionHandler: (IntroductionEditActions) -> Unit = { action ->
        coordinator.handle(action)
    }

    IntroductionEditScreen(
        state = uiState,
        onAction = actionHandler,
    )
}
