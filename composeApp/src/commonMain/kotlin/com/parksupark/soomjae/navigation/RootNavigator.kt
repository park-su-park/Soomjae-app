package com.parksupark.soomjae.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationDestination
import com.parksupark.soomjae.core.presentation.ui.navigation.loggerObserver
import com.parksupark.soomjae.core.presentation.ui.utils.hasRoute
import com.parksupark.soomjae.features.auth.presentation.navigation.AuthNavigator
import com.parksupark.soomjae.features.auth.presentation.navigation.navigateToEmailLogin
import com.parksupark.soomjae.features.auth.presentation.navigation.navigateToRegister
import com.parksupark.soomjae.features.auth.presentation.navigation.soomjaeAuthNavigator

internal sealed interface RootNavigator : AuthNavigator {
    fun onNavigationBarItemClicked(item: MainNavigationBarItem)
}

private class SoomjaeRootNavigator(
    override val navController: NavHostController,
    authNavigator: AuthNavigator,
) : RootNavigator, AuthNavigator by authNavigator {
    override fun navigateBack() {
        navController.navigateUp()
    }

    override fun onNavigationBarItemClicked(item: MainNavigationBarItem) {
        onNavigationBarItemClicked(item.route)
    }

    // <editor-fold desc="AuthNavigator">
    override fun navigateToRegister() {
        navController.navigateToRegister()
    }

    override fun navigateToEmailLogin() {
        navController.navigateToEmailLogin()
    }
    // </editor-fold>

    private fun onNavigationBarItemClicked(screen: NavigationDestination) {
        val hasItemRoute = navController.currentBackStackEntry.hasRoute(screen::class)

        if (!hasItemRoute) {
            navController.navigate(screen) {
                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    }
}

@Composable
internal fun rememberSoomjaeNavigator(): RootNavigator {
    val navController = rememberNavController().loggerObserver()

    return remember {
        SoomjaeRootNavigator(
            navController = navController,
            authNavigator = soomjaeAuthNavigator(navController),
        )
    }
}
