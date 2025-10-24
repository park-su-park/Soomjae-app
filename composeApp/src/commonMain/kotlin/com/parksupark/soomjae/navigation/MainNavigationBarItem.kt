package com.parksupark.soomjae.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.parksupark.soomjae.composeApp.resources.Res
import com.parksupark.soomjae.composeApp.resources.navigation_bar_home
import com.parksupark.soomjae.composeApp.resources.navigation_bar_post
import com.parksupark.soomjae.composeApp.resources.navigation_bar_profile
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationBarItem
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationDestination
import com.parksupark.soomjae.features.home.presentation.navigation.HomeDestination
import com.parksupark.soomjae.features.posts.aggregate.presentation.navigation.PostDestination
import com.parksupark.soomjae.features.profile.presentation.navigation.ProfileDestination
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.StringResource

data class MainNavigationBarItem(
    override val route: NavigationDestination,
    override val selectedIcon: ImageVector,
    override val unselectedIcon: ImageVector,
    override val label: StringResource,
) : NavigationBarItem {
    override fun toString(): String = label.key
}

internal val mainNavigationBarItems: ImmutableList<MainNavigationBarItem> = persistentListOf(
    MainNavigationBarItem(
        route = HomeDestination.Root,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        label = Res.string.navigation_bar_home,
    ),
    MainNavigationBarItem(
        route = PostDestination.Root,
        selectedIcon = Icons.Filled.Explore,
        unselectedIcon = Icons.Outlined.Explore,
        label = Res.string.navigation_bar_post,
    ),
    MainNavigationBarItem(
        route = ProfileDestination.Root,
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        label = Res.string.navigation_bar_profile,
    ),
)

internal fun NavigationBarItem.isMainNavigationBarItem(): Boolean =
    this is MainNavigationBarItem && mainNavigationBarItems.contains(this)
