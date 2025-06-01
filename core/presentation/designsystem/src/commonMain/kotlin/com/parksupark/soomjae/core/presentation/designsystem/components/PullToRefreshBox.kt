package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.PositionalThreshold
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoomjaePullToRefreshBox(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    state: PullToRefreshState = rememberPullToRefreshState(),
    contentAlignment: Alignment = Alignment.TopStart,
    indicator: @Composable BoxScope.() -> Unit = {
        SoomjaeIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            isRefreshing = isRefreshing,
            state = state,
        )
    },
    content: @Composable BoxScope.() -> Unit,
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier,
        state = state,
        contentAlignment = contentAlignment,
        indicator = indicator,
        content = content,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoomjaeIndicator(
    state: PullToRefreshState,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
    containerColor: Color = SoomjaePullToRefreshDefaults.containerColor,
    color: Color = SoomjaePullToRefreshDefaults.indicatorColor,
    threshold: Dp = PositionalThreshold,
) {
    Indicator(
        modifier = modifier,
        isRefreshing = isRefreshing,
        state = state,
        containerColor = containerColor,
        color = color,
        threshold = threshold,
    )
}

object SoomjaePullToRefreshDefaults {
    val containerColor: Color
        @Composable get() = SoomjaeTheme.colorScheme.background2

    val indicatorColor: Color
        @Composable get() = SoomjaeTheme.colorScheme.cta
}
