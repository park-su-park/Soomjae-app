package com.parksupark.soomjae.features.posts.member.presentation.post_write

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parksupark.soomjae.features.posts.member.presentation.navigation.MemberNavigator
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MemberPostWriteRoute(
    navigator: MemberNavigator,
    coordinator: MemberPostWriteCoordinator = koinViewModel { parametersOf(navigator) },
) {
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(MemberPostWriteState())

    val actionsHandler: (MemberPostWriteAction) -> Unit = { action ->
        coordinator.handle(action)
    }

    MemberPostWriteScreen(
        state = uiState,
        onAction = actionsHandler,
    )
}
