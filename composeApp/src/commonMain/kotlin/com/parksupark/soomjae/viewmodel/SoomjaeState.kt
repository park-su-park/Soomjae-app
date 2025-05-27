package com.parksupark.soomjae.viewmodel

import com.parksupark.soomjae.navigation.MainNavigationBarItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class SoomjaeState(
    val navigationBarItems: ImmutableList<MainNavigationBarItem> = persistentListOf(),
)
