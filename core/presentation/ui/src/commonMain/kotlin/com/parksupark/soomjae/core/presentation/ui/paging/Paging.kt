package com.parksupark.soomjae.core.presentation.ui.paging

import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
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
