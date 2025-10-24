package com.parksupark.soomjae.features.home.presentation.navigation

import androidx.navigation.NavHostController
import com.parksupark.soomjae.core.presentation.ui.navigation.SoomjaeNavigator
import com.parksupark.soomjae.core.presentation.ui.navigation.overridden

interface HomeNavigator : SoomjaeNavigator

private class SoomjaeHomeNavigator(
    override val navController: NavHostController,
) : HomeNavigator {
    override fun navigateBack() {
        overridden()
    }
}

fun soomjaeHomeNavigator(navController: NavHostController): HomeNavigator =
    SoomjaeHomeNavigator(navController)
