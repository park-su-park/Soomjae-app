package com.parksupark.soomjae.core.presentation.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource

interface NavigationBarItem {
    val route: NavigationRoutes
    val selectedIcon: ImageVector
    val unselectedIcon: ImageVector
    val label: StringResource
}
