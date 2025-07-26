package com.parksupark.soomjae.features.posts.community.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.posts.common.presentation.navigation.PostNavigator
import org.koin.compose.viewmodel.koinViewModel

class CommunityDetailCoordinator(
    private val navigator: PostNavigator,
    val viewModel: CommunityDetailViewModel,
) {
    internal val screenStateFlow = viewModel.uiStateFlow

    internal fun handle(action: CommunityDetailAction) {
        when (action) {
            CommunityDetailAction.OnBackClick -> navigator.navigateBack()

            is CommunityDetailAction.OnToggleLikeClick -> viewModel.toggleLike()
        }
    }
}

@Composable
internal fun rememberCommunityDetailCoordinator(
    navigator: PostNavigator,
    viewModel: CommunityDetailViewModel = koinViewModel(),
): CommunityDetailCoordinator = remember(navigator, viewModel) {
    CommunityDetailCoordinator(
        navigator = navigator,
        viewModel = viewModel,
    )
}
