package com.parksupark.soomjae.features.setting.presentation.navigation

import androidx.navigation.NavHostController
import com.parksupark.soomjae.core.presentation.ui.navigation.SoomjaeNavigator
import com.parksupark.soomjae.core.presentation.ui.navigation.overridden

interface SettingNavigator : SoomjaeNavigator {
    fun navigateToLogin()
}

private class SoomjaeSettingNavigator(
    override val navController: NavHostController,
) : SettingNavigator {
    override fun navigateBack() {
        overridden()
    }

    override fun navigateToLogin() {
        overridden()
    }
}

fun soomjaeSettingNavigator(navController: NavHostController): SettingNavigator =
    SoomjaeSettingNavigator(navController)
