package com.parksupark.soomjae.core.common.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.RemoteMediator

@ExperimentalPagingApi
fun <Key : Any, Value : Any> createPager(
    config: PagingConfig,
    initialKey: Key? = null,
    remoteMediator: RemoteMediator<Key, Value>?,
    pagingSourceFactory: () -> PagingSource<Key, Value>,
): Pager<Key, Value> = Pager(
    config,
    initialKey,
    remoteMediator,
    pagingSourceFactory,
)

fun <Key : Any, Value : Any> createPager(
    config: PagingConfig,
    initialKey: Key? = null,
    pagingSourceFactory: () -> PagingSource<Key, Value>,
): Pager<Key, Value> = Pager(
    config,
    initialKey,
    pagingSourceFactory,
)
