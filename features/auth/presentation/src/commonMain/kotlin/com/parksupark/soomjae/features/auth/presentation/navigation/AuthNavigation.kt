package com.parksupark.soomjae.features.auth.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationDestination
import com.parksupark.soomjae.features.auth.presentation.emaillogin.EmailLoginRoute
import com.parksupark.soomjae.features.auth.presentation.login.LoginRoute
import com.parksupark.soomjae.features.auth.presentation.register.RegisterRoute
import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthDestination : NavigationDestination {
    @Serializable
    data object Root : AuthDestination

    @Serializable
    data object Login : AuthDestination

    @Serializable
    data object Register : AuthDestination

    @Serializable
    data object EmailLogin : AuthDestination
}

fun NavGraphBuilder.authGraph(navigator: AuthNavigator) {
    navigation<AuthDestination.Root>(
        startDestination = AuthDestination.Login,
    ) {
        composable<AuthDestination.Login> {
            LoginRoute(navigator)
        }
        composable<AuthDestination.Register> {
            RegisterRoute(navigator)
        }
        composable<AuthDestination.EmailLogin> {
            EmailLoginRoute(navigator)
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

fun NavHostController.navigateToRegister() {
    navigate(AuthDestination.Register)
}

fun NavHostController.navigateToEmailLogin() {
    navigate(AuthDestination.EmailLogin) {
        popUpTo(AuthDestination.Login) {
            inclusive = false
        }
    }
}
