package com.parksupark.soomjae.core.presentation.ui.utils

import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.flowOf

@Composable
fun <T : Any> emptyLazyPagingItems(): LazyPagingItems<T> = flowOf(
    PagingData.empty<T>(),
).collectAsLazyPagingItems()
