package com.parksupark.soomjae.features.posts.community.presentation.write

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.core.presentation.ui.components.SoomjaeSnackbarHost
import com.parksupark.soomjae.core.presentation.ui.components.showSnackbar
import com.parksupark.soomjae.features.posts.community.presentation.navigation.CommunityNavigator
import kotlinx.coroutines.launch

@Composable
fun CommunityWriteRoute(
    navigator: CommunityNavigator,
    coordinator: CommunityWriteCoordinator = rememberCommunityWriteCoordinator(navigator),
) {
    val uiState by coordinator.screenStateFlow.collectAsState(CommunityWriteState())
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val actionsHandler: (CommunityWriteAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    ObserveAsEvents(
        coordinator.eventFlow,
    ) {
        when (it) {
            is CommunityWriteEvent.Error -> coroutineScope.launch {
                snackbarHostState.showSnackbar(it.message, isError = true)
            }

            is CommunityWriteEvent.PostSubmitted -> navigator.navigateToCommunityDetail(it.postId)
        }
    }

    CommunityWriteScreen(
        state = uiState,
        onAction = actionsHandler,
        snackbarHost = { SoomjaeSnackbarHost(snackbarHostState) },
    )
}
