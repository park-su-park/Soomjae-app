package com.parksupark.soomjae.core.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.koin.compose.koinInject

@Composable
fun <T> ObserveAsEvents(
    flow: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    dispatcher: SoomjaeDispatcher = koinInject(),
    onEvent: (T) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(flow, lifecycleOwner.lifecycle, key1, key2) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(dispatcher.mainImmediate) {
                flow.collect(onEvent)
            }
        }
    }
}
