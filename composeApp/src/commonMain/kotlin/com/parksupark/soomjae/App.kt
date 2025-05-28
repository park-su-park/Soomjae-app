package com.parksupark.soomjae

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.ui.components.SoomjaeBottomNavigationBar
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationBarItem.Companion.hasRoute
import com.parksupark.soomjae.features.auth.presentation.navigation.authGraph
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
                if (item.isMainNavigationBarItem()) {
                    navigator.onNavigationBarItemClicked(item)
                }
            },
        )
    }

    SoomjaeScaffold {
        NavHost(
            navController = navigator.navController,
            startDestination = HomeDestination.Root,
        ) {
            homeGraph(navigator, bottomBar)
            authGraph(navigator)
            profileGraph(navigator, bottomBar)
        }
    }
}
