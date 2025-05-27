package com.parksupark.soomjae.features.home.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationDestination
import com.parksupark.soomjae.features.home.presentation.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeDestination : NavigationDestination {
    @Serializable
    data object Root : HomeDestination

    @Serializable
    data object Home : HomeDestination
}

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation<HomeDestination.Root>(
        startDestination = HomeDestination.Home,
    ) {
        composable<HomeDestination.Home> {
            HomeRoute()
        }
    }
}
