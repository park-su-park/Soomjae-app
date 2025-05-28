package com.parksupark.soomjae

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import co.touchlab.kermit.Logger
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.ui.components.SoomjaeBottomNavigationBar
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationBarItem.Companion.hasRoute
import com.parksupark.soomjae.features.auth.presentation.navigation.authGraph
import com.parksupark.soomjae.features.auth.presentation.navigation.navigateToLogin
import com.parksupark.soomjae.features.home.presentation.navigation.HomeDestination
import com.parksupark.soomjae.features.home.presentation.navigation.homeGraph
import com.parksupark.soomjae.features.profile.presentation.navigation.profileGraph
import com.parksupark.soomjae.navigation.isMainNavigationBarItem
import com.parksupark.soomjae.navigation.rememberSoomjaeNavigator
import com.parksupark.soomjae.viewmodel.SoomjaeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun App(viewModel: SoomjaeViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val navigator = rememberSoomjaeNavigator()
    val navHostController = navigator.navController
    val currentNav by navHostController.currentBackStackEntryAsState()

    val bottomBar = @Composable {
        SoomjaeBottomNavigationBar(
            items = uiState.navigationBarItems,
            isSelected = { item -> currentNav.hasRoute(item) },
            onClick = { item ->
                Logger.d(tag = "App", messageString = "Navigation bar item clicked: ${item.label}")

                if (item.isMainNavigationBarItem()) {
                    navHostController.navigate(item.route) {
                        popUpTo(HomeDestination.Root) {
                            inclusive = true
                        }
                    }
                }
            },
        )
    }

    SoomjaeScaffold {
        NavHost(
            navController = navigator.navController,
            startDestination = HomeDestination.Root,
        ) {
            homeGraph(navHostController, bottomBar)
            authGraph(navigator)
            profileGraph(bottomBar, navigateToLogin = navHostController::navigateToLogin)
        }
    }
}
