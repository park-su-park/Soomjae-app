package com.parksupark.soomjae.features.profile.presentation.profile_edit

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.core.presentation.ui.components.showSnackbar
import com.parksupark.soomjae.features.profile.presentation.navigation.ProfileNavigator
import kotlinx.coroutines.launch

@Composable
fun ProfileEditRoute(
    navigator: ProfileNavigator,
    coordinator: ProfileEditCoordinator = rememberProfileEditCoordinator(navigator = navigator),
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(ProfileEditState())
    val canSubmit by coordinator.canSubmit.collectAsStateWithLifecycle(initialValue = false)
    val events = coordinator.eventFlow

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val actionsHandler: (ProfileEditAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    ObserveAsEvents(
        flow = events,
        onEvent = { event ->
            when (event) {
                is ProfileEditEvent.SubmitSuccess -> coordinator.navigateBack()

                is ProfileEditEvent.GetProfileFailed -> {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(uiText = event.message, isError = true)
                    }
                    coordinator.navigateBack()
                }

                is ProfileEditEvent.ShowError -> coroutineScope.launch {
                    snackbarHostState.showSnackbar(uiText = event.message, isError = true)
                }
            }
        },
    )

    ProfileEditScreen(
        state = uiState,
        canSubmit = canSubmit,
        onAction = actionsHandler,
    )
}
