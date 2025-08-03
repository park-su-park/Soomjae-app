package com.parksupark.soomjae.features.posts.aggregate.presentation.post

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.posts.aggregate.presentation.navigation.PostNavigator
import com.parksupark.soomjae.features.posts.common.presentation.PostAction
import org.koin.compose.viewmodel.koinViewModel

internal class PostCoordinator(
    private val navigator: PostNavigator,
    val viewModel: PostViewModel,
) {
    val screenStateFlow = viewModel.uiStateFlow

    fun handle(action: PostAction) {
        when (action) {
            PostAction.OnClick -> { // Handle action
            }

            is PostAction.NavigateToCommunityDetail -> navigator.navigateToCommunityDetail(action.postId)

            PostAction.NavigateToCommunityWrite -> navigator.navigateToCommunityWrite()

            PostAction.NavigateToMeetingWrite -> navigator.navigateToMeetingWrite()
        }
    }
}

@Composable
internal fun rememberPostCoordinator(
    navigator: PostNavigator,
    viewModel: PostViewModel = koinViewModel(),
): PostCoordinator = remember(viewModel) {
    PostCoordinator(
        navigator = navigator,
        viewModel = viewModel,
    )
}
