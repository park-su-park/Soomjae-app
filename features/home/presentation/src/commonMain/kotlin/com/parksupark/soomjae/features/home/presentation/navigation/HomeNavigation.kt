package com.parksupark.soomjae.features.home.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.parksupark.soomjae.features.home.presentation.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeRoutes {
    @Serializable
    data object Root : HomeRoutes

    @Serializable
    data object Home : HomeRoutes
}

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation<HomeRoutes.Root>(
        startDestination = HomeRoutes.Home,
    ) {
        composable<HomeRoutes.Home> {
            HomeRoute()
        }
    }
}
