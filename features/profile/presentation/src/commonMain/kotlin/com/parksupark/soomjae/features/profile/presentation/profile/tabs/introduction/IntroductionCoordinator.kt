package com.parksupark.soomjae.features.profile.presentation.profile.tabs.introduction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.profile.presentation.profile.ProfileAction
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

class IntroductionCoordinator(
    private val memberId: Long,
    private val onProfileAction: (ProfileAction) -> Unit,
    val viewModel: IntroductionViewModel,
) {
    val screenStateFlow = viewModel.stateFlow

    fun handle(action: IntroductionAction) {
        when (action) {
            IntroductionAction.OnPullToRefresh -> viewModel.refreshPost()

            IntroductionAction.OnEditClick -> {
                onProfileAction(ProfileAction.OnEditIntroductionClick(memberId))
            }
        }
    }
}

@Composable
fun rememberIntroductionCoordinator(
    userId: Long,
    onAction: (ProfileAction) -> Unit,
    viewModel: IntroductionViewModel = koinViewModel(
        key = "introduction_view_model_$userId",
        parameters = {
            parametersOf(userId)
        },
    ),
): IntroductionCoordinator = remember(userId, onAction, viewModel) {
    IntroductionCoordinator(
        memberId = userId,
        onProfileAction = onAction,
        viewModel = viewModel,
    )
}
