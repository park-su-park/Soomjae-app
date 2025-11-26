package com.parksupark.soomjae.core.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.paging.LoadState

@Composable
fun RefreshEffect(
    refreshState: LoadState,
    isRefreshing: Boolean,
    onRefreshChange: (Boolean) -> Unit,
) {
    LaunchedEffect(refreshState, isRefreshing, onRefreshChange) {
        if (refreshState is LoadState.NotLoading && isRefreshing) {
            onRefreshChange(false)
        }
    }
}

@Composable
fun RefreshEffect(
    refreshState: LoadState,
    isRefreshing: Boolean,
    vararg keys: Any?,
    onRefreshChange: (Boolean) -> Unit,
) {
    LaunchedEffect(
        refreshState,
        isRefreshing,
        onRefreshChange,
        *keys,
    ) {
        if (refreshState is LoadState.NotLoading && isRefreshing) {
            onRefreshChange(false)
        }
    }
}
