package com.parksupark.soomjae.features.profile.presentation.introduction_edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.parksupark.soomjae.features.profile.presentation.navigation.ProfileNavigator
import org.koin.compose.viewmodel.koinViewModel

class IntroductionEditCoordinator(
    val viewModel: IntroductionEditViewModel,
    private val navigator: ProfileNavigator,
) {
    val screenStateFlow = viewModel.stateFlow

    fun handle(action: IntroductionEditActions) {
        when (action) {
            IntroductionEditActions.OnBackClick -> navigator.navigateBack()
        }
    }
}

@Composable
fun rememberIntroductionEditCoordinator(
    navigator: ProfileNavigator,
    viewModel: IntroductionEditViewModel = koinViewModel(),
): IntroductionEditCoordinator = remember(navigator, viewModel) {
    IntroductionEditCoordinator(
        viewModel = viewModel,
        navigator = navigator,
    )
}
