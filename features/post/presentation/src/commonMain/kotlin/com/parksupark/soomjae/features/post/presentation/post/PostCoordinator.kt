package com.parksupark.soomjae.features.post.presentation.post

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.compose.viewmodel.koinViewModel

class PostCoordinator(
    val viewModel: PostViewModel,
) {
    val screenStateFlow = viewModel.uiStateFlow

    fun handle(action: PostAction) {
        when (action) {
            PostAction.OnClick -> { // Handle action
            }
        }
    }
}

@Composable
fun rememberPostCoordinator(viewModel: PostViewModel = koinViewModel()): PostCoordinator = remember(viewModel) {
    PostCoordinator(
        viewModel = viewModel,
    )
}
