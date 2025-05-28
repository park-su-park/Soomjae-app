package com.parksupark.soomjae.features.profile.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationDestination
import com.parksupark.soomjae.features.profile.presentation.profile.ProfileRoute
import kotlinx.serialization.Serializable

@Serializable
sealed class ProfileDestination : NavigationDestination {
    @Serializable
    data object Root : ProfileDestination()

    @Serializable
    data object Profile : ProfileDestination()
}

fun NavGraphBuilder.profileGraph(
    navigator: ProfileNavigator,
    bottomBar: @Composable () -> Unit,
) {
    navigation<ProfileDestination.Root>(startDestination = ProfileDestination.Profile) {
        composable<ProfileDestination.Profile> {
            ProfileRoute(navigator, bottomBar = bottomBar)
        }
    }
}

fun NavHostController.navigateToProfile() {
    navigate(ProfileDestination.Profile) {
        popUpTo(ProfileDestination.Root) {
            inclusive = true
        }
    }
}
