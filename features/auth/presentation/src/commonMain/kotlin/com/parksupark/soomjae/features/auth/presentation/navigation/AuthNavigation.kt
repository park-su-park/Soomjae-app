package com.parksupark.soomjae.features.auth.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationDestination
import com.parksupark.soomjae.features.auth.presentation.email_verification.EmailVerificationRoute
import com.parksupark.soomjae.features.auth.presentation.emaillogin.EmailLoginRoute
import com.parksupark.soomjae.features.auth.presentation.login.LoginRoute
import com.parksupark.soomjae.features.auth.presentation.register.RegisterRoute
import com.parksupark.soomjae.features.auth.presentation.register.RegisterViewModel
import com.parksupark.soomjae.features.auth.presentation.register.rememberRegisterCoordinator
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
sealed interface AuthDestination : NavigationDestination {
    @Serializable
    data object Root : AuthDestination

    @Serializable
    data object Login : AuthDestination

    @Serializable
    data object RegisterRoot : AuthDestination

    @Serializable
    data class Register(val email: String) : AuthDestination

    @Serializable
    data class EmailLogin(val email: String? = null) : AuthDestination

    @Serializable
    data object EmailVerification : AuthDestination
}

fun NavGraphBuilder.authGraph(navigator: AuthNavigator) {
    navigation<AuthDestination.Root>(
        startDestination = AuthDestination.Login,
    ) {
        composable<AuthDestination.Login> {
            LoginRoute(navigator)
        }
        composable<AuthDestination.EmailLogin> {
            EmailLoginRoute(navigator)
        }

        emailRegistrationGraph(navigator)
    }
}

private fun NavGraphBuilder.emailRegistrationGraph(navigator: AuthNavigator) {
    navigation<AuthDestination.RegisterRoot>(
        startDestination = AuthDestination.EmailVerification,
    ) {
        composable<AuthDestination.EmailVerification> {
            EmailVerificationRoute(navigator)
        }
        composable<AuthDestination.Register> { backStackEntry ->
            val email = backStackEntry.toRoute<AuthDestination.Register>().email
            val viewModel = koinViewModel<RegisterViewModel> {
                parametersOf(email)
            }
            RegisterRoute(
                navigator = navigator,
                coordinator = rememberRegisterCoordinator(navigator, viewModel),
            )
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
    navigate(AuthDestination.RegisterRoot)
}

fun NavHostController.navigateToEmailLogin(email: String? = null) {
    navigate(AuthDestination.EmailLogin(email)) {
        popUpTo(AuthDestination.Login) {
            inclusive = false
        }
    }
}
