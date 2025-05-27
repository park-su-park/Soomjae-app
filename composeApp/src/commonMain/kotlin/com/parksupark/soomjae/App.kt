package com.parksupark.soomjae

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.ui.components.SoomjaeBottomNavigationBar
import com.parksupark.soomjae.core.presentation.ui.navigation.loggerObserver
import com.parksupark.soomjae.features.auth.presentation.navigation.authGraph
import com.parksupark.soomjae.features.auth.presentation.navigation.navigateToLogin
import com.parksupark.soomjae.features.home.presentation.navigation.HomeDestination
import com.parksupark.soomjae.features.home.presentation.navigation.homeGraph
import com.parksupark.soomjae.features.profile.presentation.navigation.profileGraph
import com.parksupark.soomjae.viewmodel.SoomjaeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun App(viewModel: SoomjaeViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val navHostController = rememberNavController().loggerObserver()

    val bottomBar = @Composable {
        SoomjaeBottomNavigationBar(
            items = uiState.navigationBarItems,
            isSelected = { item -> false },
            onClick = { item -> { /* TODO */ } },
        )
    }

    SoomjaeScaffold(bottomBar = bottomBar) {
        NavHost(
            navController = navHostController,
            startDestination = HomeDestination.Root,
        ) {
            homeGraph(navHostController)
            authGraph(navHostController)
            profileGraph(navigateToLogin = navHostController::navigateToLogin)
        }
    }
}
