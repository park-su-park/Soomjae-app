package com.parksupark.soomjae.features.post.presentation.communitydetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.post.presentation.navigation.PostNavigator
import org.koin.compose.viewmodel.koinViewModel

internal class CommunityDetailCoordinator(
    private val navigator: PostNavigator,
    val viewModel: CommunityDetailViewModel,
) {
    val screenStateFlow = viewModel.uiStateFlow

    fun handle(action: CommunityDetailAction) {
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
