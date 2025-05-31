package com.parksupark.soomjae.features.post.presentation.post

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.post.presentation.navigation.PostNavigator
import org.koin.compose.viewmodel.koinViewModel

class PostCoordinator(
    private val navigator: PostNavigator,
    val viewModel: PostViewModel,
) {
    val screenStateFlow = viewModel.uiStateFlow

    fun handle(action: PostAction) {
        when (action) {
            PostAction.OnClick -> { // Handle action
            }

            PostAction.OnCommunityWriteClick -> navigator.navigateToCommunityWrite()
        }
    }
}

@Composable
fun rememberPostCoordinator(
    navigator: PostNavigator,
    viewModel: PostViewModel = koinViewModel(),
): PostCoordinator = remember(viewModel) {
    PostCoordinator(
        navigator = navigator,
        viewModel = viewModel,
    )
}
