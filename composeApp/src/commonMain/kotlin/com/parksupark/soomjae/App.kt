package com.parksupark.soomjae

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.ui.ObserveAsEvents
import com.parksupark.soomjae.core.presentation.ui.components.LoginRequestDialog
import com.parksupark.soomjae.core.presentation.ui.components.SoomjaeBottomNavigationBar
import com.parksupark.soomjae.core.presentation.ui.controllers.SoomjaeEvent
import com.parksupark.soomjae.core.presentation.ui.controllers.SoomjaeEventController
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationBarItem.Companion.hasRoute
import com.parksupark.soomjae.features.auth.presentation.navigation.authGraph
import com.parksupark.soomjae.features.home.presentation.navigation.HomeDestination
import com.parksupark.soomjae.features.home.presentation.navigation.homeGraph
import com.parksupark.soomjae.features.post.presentation.navigation.postGraph
import com.parksupark.soomjae.features.profile.presentation.navigation.profileGraph
import com.parksupark.soomjae.features.setting.presentation.navigation.settingGraph
import com.parksupark.soomjae.navigation.isMainNavigationBarItem
import com.parksupark.soomjae.navigation.rememberSoomjaeNavigator
import com.parksupark.soomjae.viewmodel.SoomjaeViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun App(
    viewModel: SoomjaeViewModel = koinViewModel(),
    soomjaeEventController: SoomjaeEventController = koinInject(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showLoginDialog by remember { mutableStateOf(false) }

    ObserveAsEvents(
        flow = soomjaeEventController.eventChannel,
    ) {
        when (it) {
            SoomjaeEvent.LoginRequest -> {
                showLoginDialog = true
            }
        }
    }

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

    AppTheme(theme = uiState.theme) {
        SoomjaeScaffold {
            NavHost(
                navController = navigator.navController,
                startDestination = HomeDestination.Root,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None },
            ) {
                homeGraph(navigator, bottomBar)
                authGraph(navigator)
                postGraph(navigator, bottomBar)
                profileGraph(navigator, bottomBar)
                settingGraph(navigator)
            }
        }

        AnimatedVisibility(
            visible = showLoginDialog,
            exit = ExitTransition.None,
        ) {
            LoginRequestDialog(
                onCancelClick = { showLoginDialog = false },
                onConfirmClick = {
                    showLoginDialog = false
                    navigator.navigateToLogin()
                },
            )
        }
    }
}
