package com.parksupark.soomjae.features.profile.presentation.profile.tabs.introduction

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parksupark.soomjae.features.profile.presentation.profile.ProfileAction

@Composable
fun IntroductionTab(
    userId: Long,
    onAction: (ProfileAction) -> Unit,
    coordinator: IntroductionCoordinator = rememberIntroductionCoordinator(
        userId = userId,
        onAction = onAction,
    ),
    listState: LazyListState,
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(IntroductionState())

    val actionsHandler: (IntroductionAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    ProfileIntroductionScreen(
        state = uiState,
        onAction = actionsHandler,
        listState = listState,
    )
}
