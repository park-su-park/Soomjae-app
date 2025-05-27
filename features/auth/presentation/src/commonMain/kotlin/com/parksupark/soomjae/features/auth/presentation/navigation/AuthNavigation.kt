package com.parksupark.soomjae.features.auth.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationRoutes
import com.parksupark.soomjae.features.auth.presentation.login.LoginRoute
import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthRoutes : NavigationRoutes {
    @Serializable
    data object Root : AuthRoutes

    @Serializable
    data object Login : AuthRoutes
}

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation<AuthRoutes.Root>(
        startDestination = AuthRoutes.Login,
    ) {
        composable<AuthRoutes.Login> {
            LoginRoute()
        }
    }
}
