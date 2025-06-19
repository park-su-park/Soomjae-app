package com.parksupark.soomjae.features.setting.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationDestination
import com.parksupark.soomjae.features.setting.presentation.setting.SettingRoute
import kotlinx.serialization.Serializable

private sealed interface SettingDestination : NavigationDestination {
    @Serializable
    data object Root : SettingDestination

    @Serializable
    data object Setting : SettingDestination
}

fun NavGraphBuilder.settingGraph(navigator: SettingNavigator) {
    navigation<SettingDestination.Root>(startDestination = SettingDestination.Setting) {
        composable<SettingDestination.Setting> {
            SettingRoute(navigator = navigator)
        }
    }
}

fun NavController.navigateToSetting() {
    navigate(SettingDestination.Setting) {
        popUpTo(SettingDestination.Root) {
            inclusive = true
        }
    }
}
