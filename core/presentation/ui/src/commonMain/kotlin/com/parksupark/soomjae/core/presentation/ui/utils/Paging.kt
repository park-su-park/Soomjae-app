package com.parksupark.soomjae.core.presentation.ui.utils

import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.flowOf

@Composable
fun <T : Any> emptyLazyPagingItems(): LazyPagingItems<T> = flowOf(
    PagingData.empty<T>(),
).collectAsLazyPagingItems()

@Composable
fun <T : Any> lazyPagingItemsOf(items: ImmutableList<T>): LazyPagingItems<T> = flowOf(
    PagingData.from(items),
).collectAsLazyPagingItems()
