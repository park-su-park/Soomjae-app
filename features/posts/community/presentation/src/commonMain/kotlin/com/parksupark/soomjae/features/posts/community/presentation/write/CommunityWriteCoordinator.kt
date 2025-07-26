package com.parksupark.soomjae.features.posts.community.presentation.write

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.posts.common.presentation.navigation.PostNavigator
import org.koin.compose.viewmodel.koinViewModel

class CommunityWriteCoordinator(
    private val navigator: PostNavigator,
    val viewModel: CommunityWriteViewModel,
) {
    internal val screenStateFlow = viewModel.uiStateFlow
    internal val eventFlow = viewModel.eventChannel

    internal fun handle(action: CommunityWriteAction) {
        when (action) {
            CommunityWriteAction.OnBackClick -> navigator.navigateBack()

            CommunityWriteAction.OnConfirmClick -> viewModel.submitPost()

            is CommunityWriteAction.OnCategorySelected -> viewModel.selectCategory(action.categoryId)
        }
    }
}

@Composable
internal fun rememberCommunityWriteCoordinator(
    navigator: PostNavigator,
    viewModel: CommunityWriteViewModel = koinViewModel(),
): CommunityWriteCoordinator = remember(viewModel) {
    CommunityWriteCoordinator(
        navigator = navigator,
        viewModel = viewModel,
    )
}
