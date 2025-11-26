package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoomjaeTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    expandedHeight: Dp = TopAppBarDefaults.TopAppBarExpandedHeight,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = SoomjaeTopAppBarDefaults.topAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        expandedHeight = expandedHeight,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoomjaeCenterAlignedTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    expandedHeight: Dp = TopAppBarDefaults.TopAppBarExpandedHeight,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = SoomjaeTopAppBarDefaults.topAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    CenterAlignedTopAppBar(
        title = {
            CompositionLocalProvider(
                LocalTextStyle provides SoomjaeTheme.typography.labelL,
                LocalContentColor provides SoomjaeTheme.colorScheme.text2,
            ) {
                title()
            }
        },
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        expandedHeight = expandedHeight,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
object SoomjaeTopAppBarDefaults {
    @Composable
    fun topAppBarColors() = TopAppBarColors(
        containerColor = SoomjaeTheme.colorScheme.background1,
        scrolledContainerColor = SoomjaeTheme.colorScheme.background1,
        navigationIconContentColor = SoomjaeTheme.colorScheme.icon,
        titleContentColor = SoomjaeTheme.colorScheme.text2,
        actionIconContentColor = SoomjaeTheme.colorScheme.icon,
    )
}
