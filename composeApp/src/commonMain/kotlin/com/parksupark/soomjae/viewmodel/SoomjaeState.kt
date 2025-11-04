package com.parksupark.soomjae.viewmodel

import com.parksupark.soomjae.core.common.theme.ColorTheme
import com.parksupark.soomjae.navigation.MainNavigationBarItem
import com.parksupark.soomjae.navigation.mainNavigationBarItems
import kotlinx.collections.immutable.ImmutableList

internal data class SoomjaeState(
    val theme: ColorTheme = ColorTheme.SYSTEM,
    val navigationBarItems: ImmutableList<MainNavigationBarItem> = mainNavigationBarItems,
)
