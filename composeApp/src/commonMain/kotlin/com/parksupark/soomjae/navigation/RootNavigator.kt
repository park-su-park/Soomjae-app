package com.parksupark.soomjae.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationBarItem
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationDestination
import com.parksupark.soomjae.core.presentation.ui.navigation.loggerObserver
import com.parksupark.soomjae.core.presentation.ui.utils.hasRoute
import com.parksupark.soomjae.features.auth.presentation.navigation.AuthDestination
import com.parksupark.soomjae.features.auth.presentation.navigation.AuthNavigator
import com.parksupark.soomjae.features.auth.presentation.navigation.navigateToEmailLogin
import com.parksupark.soomjae.features.auth.presentation.navigation.navigateToLogin
import com.parksupark.soomjae.features.auth.presentation.navigation.navigateToRegister
import com.parksupark.soomjae.features.auth.presentation.navigation.soomjaeAuthNavigator
import com.parksupark.soomjae.features.home.presentation.navigation.HomeNavigator
import com.parksupark.soomjae.features.home.presentation.navigation.soomjaeHomeNavigator
import com.parksupark.soomjae.features.post.presentation.navigation.PostNavigator
import com.parksupark.soomjae.features.post.presentation.navigation.soomjaePostNavigator
import com.parksupark.soomjae.features.profile.presentation.navigation.ProfileNavigator
import com.parksupark.soomjae.features.profile.presentation.navigation.soomjaeProfileNavigator

internal sealed interface RootNavigator : AuthNavigator, HomeNavigator, PostNavigator, ProfileNavigator {
    fun onNavigationBarItemClicked(item: NavigationBarItem)
}

private class SoomjaeRootNavigator(
    override val navController: NavHostController,
    authNavigator: AuthNavigator,
    homeNavigator: HomeNavigator,
    postNavigator: PostNavigator,
    profileNavigator: ProfileNavigator,
) : RootNavigator,
    AuthNavigator by authNavigator,
    HomeNavigator by homeNavigator,
    PostNavigator by postNavigator,
    ProfileNavigator by profileNavigator {
    override fun navigateBack() {
        navController.navigateUp()
    }

    override fun onNavigationBarItemClicked(item: NavigationBarItem) {
        onNavigationBarItemClicked(item.route)
    }

    // <editor-fold desc="AuthNavigator">
    override fun navigateToRegister() {
        navController.navigateToRegister()
    }

    override fun navigateToEmailLogin() {
        navController.navigateToEmailLogin()
    }

    override fun popUpAuthGraph() {
        navController.popBackStack(AuthDestination.Root, inclusive = true)
    }

    // </editor-fold>

    // <editor-fold desc="ProfileNavigator">
    override fun navigateToLogin() {
        navController.navigateToLogin()
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
            homeNavigator = soomjaeHomeNavigator(navController),
            postNavigator = soomjaePostNavigator(navController),
            profileNavigator = soomjaeProfileNavigator(navController),
        )
    }
}
