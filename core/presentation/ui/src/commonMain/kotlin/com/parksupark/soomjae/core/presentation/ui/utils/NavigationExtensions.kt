package com.parksupark.soomjae.core.presentation.ui.utils

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationDestination
import kotlin.reflect.KClass

fun <T : NavigationDestination> NavBackStackEntry?.hasRoute(route: KClass<T>) =
    this?.destination?.hasRoute(route) == true

fun <T : NavigationDestination> NavBackStackEntry?.hasParentRoute(route: KClass<T>) =
    this?.destination?.parent?.hasRoute(route) == true
