package com.parksupark.soomjae.features.posts.community.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.posts.community.presentation.navigation.CommunityNavigator
import org.koin.compose.viewmodel.koinViewModel

class CommunityDetailCoordinator(
    private val navigator: CommunityNavigator,
    val viewModel: CommunityDetailViewModel,
) {
    val screenStateFlow = viewModel.uiStateFlow
    val eventFlow = viewModel.eventChannel

    internal fun handle(action: CommunityDetailAction) {
        when (action) {
            CommunityDetailAction.OnBackClick -> navigator.navigateBack()

            CommunityDetailAction.OnDeleteClick -> viewModel.deletePost()
            CommunityDetailAction.OnEditClick -> {
                // TODO
            }

            is CommunityDetailAction.OnToggleLikeClick -> viewModel.toggleLike()

            CommunityDetailAction.OnCommentFieldClick -> viewModel.handleCommentFieldClick()

            CommunityDetailAction.OnSendCommentClick -> viewModel.submitComment()
        }
    }
}

@Composable
internal fun rememberCommunityDetailCoordinator(
    navigator: CommunityNavigator,
    viewModel: CommunityDetailViewModel = koinViewModel(),
): CommunityDetailCoordinator = remember(navigator, viewModel) {
    CommunityDetailCoordinator(
        navigator = navigator,
        viewModel = viewModel,
    )
}
