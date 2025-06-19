package com.parksupark.soomjae.viewmodel

import com.parksupark.soomjae.core.common.theme.ColorTheme
import com.parksupark.soomjae.navigation.MainNavigationBarItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class SoomjaeState(
    val theme: ColorTheme = ColorTheme.SYSTEM,
    val navigationBarItems: ImmutableList<MainNavigationBarItem> = persistentListOf(),
)
