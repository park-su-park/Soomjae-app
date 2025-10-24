package com.parksupark.soomjae.features.posts.community.presentation.write

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.posts.community.presentation.navigation.CommunityNavigator
import org.koin.compose.viewmodel.koinViewModel

class CommunityWriteCoordinator(
    private val navigator: CommunityNavigator,
    val viewModel: CommunityWriteViewModel,
) {
    internal val screenStateFlow = viewModel.uiStateFlow
    internal val eventFlow = viewModel.events

    internal fun handle(action: CommunityWriteAction) {
        when (action) {
            CommunityWriteAction.OnBackClick -> navigator.navigateBack()

            CommunityWriteAction.OnConfirmClick -> viewModel.submitPost()

            is CommunityWriteAction.OnCategorySelected -> viewModel.selectCategory(
                action.categoryId,
            )

            is CommunityWriteAction.OnLocationSelected -> viewModel.selectLocation(
                action.locationCode,
            )
        }
    }
}

@Composable
internal fun rememberCommunityWriteCoordinator(
    navigator: CommunityNavigator,
    viewModel: CommunityWriteViewModel = koinViewModel(),
): CommunityWriteCoordinator = remember(viewModel) {
    CommunityWriteCoordinator(
        navigator = navigator,
        viewModel = viewModel,
    )
}
