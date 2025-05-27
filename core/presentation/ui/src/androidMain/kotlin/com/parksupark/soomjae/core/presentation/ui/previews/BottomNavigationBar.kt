package com.parksupark.soomjae.core.presentation.ui.previews

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.ui.components.SoomjaeBottomNavigationBar
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationBarItem
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationDestination
import com.parksupark.soomjae.core.presentation.ui.resources.Res
import com.parksupark.soomjae.core.presentation.ui.resources.preview_navigation_home
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.StringResource

@Preview
@Composable
private fun SoomjaeBottomNavigationBarPreview() {
    AppTheme {
        SoomjaeBottomNavigationBar(
            items = previewNavigationBarItems,
            isSelected = { false },
            onClick = { },
        )
    }
}

private class PreviewNavigationBarItem(
    override val route: NavigationDestination,
    override val selectedIcon: ImageVector,
    override val unselectedIcon: ImageVector,
    override val label: StringResource,
) : NavigationBarItem {
    override fun equals(other: Any?): Boolean = other is PreviewNavigationBarItem && other.route == route

    override fun hashCode(): Int = route.hashCode()
}

private val previewNavigationBarItems = persistentListOf(
    PreviewNavigationBarItem(
        route = object : NavigationDestination {},
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        label = Res.string.preview_navigation_home,
    ),
    PreviewNavigationBarItem(
        route = object : NavigationDestination {},
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        label = Res.string.preview_navigation_home,
    ),
    PreviewNavigationBarItem(
        route = object : NavigationDestination {},
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        label = Res.string.preview_navigation_home,
    ),
    PreviewNavigationBarItem(
        route = object : NavigationDestination {},
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        label = Res.string.preview_navigation_home,
    ),
    PreviewNavigationBarItem(
        route = object : NavigationDestination {},
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        label = Res.string.preview_navigation_home,
    ),
)
