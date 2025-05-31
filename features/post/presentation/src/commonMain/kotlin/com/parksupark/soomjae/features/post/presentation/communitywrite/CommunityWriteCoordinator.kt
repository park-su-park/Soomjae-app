package com.parksupark.soomjae.features.post.presentation.communitywrite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.post.presentation.navigation.PostNavigator
import org.koin.compose.viewmodel.koinViewModel

class CommunityWriteCoordinator(
    private val navigator: PostNavigator,
    val viewModel: CommunityWriteViewModel,
) {
    val screenStateFlow = viewModel.uiStateFlow
    val eventFlow = viewModel.eventChannel

    fun handle(action: CommunityWriteAction) {
        when (action) {
            CommunityWriteAction.OnBackClick -> navigator.navigateBack()

            CommunityWriteAction.OnConfirmClick -> viewModel.submitPost()
        }
    }
}

@Composable
fun rememberCommunityWriteCoordinator(
    navigator: PostNavigator,
    viewModel: CommunityWriteViewModel = koinViewModel(),
): CommunityWriteCoordinator = remember(viewModel) {
    CommunityWriteCoordinator(
        navigator = navigator,
        viewModel = viewModel,
    )
}
