package com.parksupark.soomjae.features.auth.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationDestination
import com.parksupark.soomjae.features.auth.presentation.login.LoginRoute
import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthDestination : NavigationDestination {
    @Serializable
    data object Root : AuthDestination

    @Serializable
    data object Login : AuthDestination
}

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation<AuthDestination.Root>(
        startDestination = AuthDestination.Login,
    ) {
        composable<AuthDestination.Login> {
            LoginRoute()
        }
    }
}

fun NavHostController.navigateToLogin() {
    navigate(AuthDestination.Login) {
        popUpTo(AuthDestination.Root) {
            inclusive = true
        }
    }
}
