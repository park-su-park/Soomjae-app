package com.parksupark.soomjae.core.presentation.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import com.parksupark.soomjae.core.presentation.ui.utils.hasParentRoute
import com.parksupark.soomjae.core.presentation.ui.utils.hasRoute
import org.jetbrains.compose.resources.StringResource

interface NavigationBarItem {
    val route: NavigationDestination
    val selectedIcon: ImageVector
    val unselectedIcon: ImageVector
    val label: StringResource

    companion object {
        fun <T : NavigationBarItem> NavBackStackEntry?.hasRoute(screen: T) =
            hasRoute(screen.route::class) || hasParentRoute(screen.route::class)
    }
}
